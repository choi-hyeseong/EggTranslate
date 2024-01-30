package com.example.demo.user.parent.service

import com.example.demo.logger
import com.example.demo.profile.dto.ParentEditDTO
import com.example.demo.translate.auto.service.TranslateManageService
import com.example.demo.translate.auto.service.TranslateResultService
import com.example.demo.user.basic.exception.UserNotFoundException
import com.example.demo.user.basic.service.UserService
import com.example.demo.user.parent.child.dto.ChildDTO
import com.example.demo.user.parent.dto.ParentDTO
import com.example.demo.user.parent.entity.Parent
import com.example.demo.user.parent.repository.ParentRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import kotlin.jvm.optionals.getOrNull

@Service
class ParentService(
    private val userService: UserService,
    private val parentRepository: ParentRepository,
    private val translateManageService: TranslateManageService
) {

    val log = logger()

    @Transactional
    suspend fun signUp(parentDTO: ParentDTO): Long? {
        return if (existParent(parentDTO.user.id!!))
            null
        else {
            val user = userService.getUserEntity(parentDTO.user.id!!)
            parentRepository.save(parentDTO.toEntity(user, parentDTO.children.map { it.toEntity() }.toMutableList())).id
        }
    }

    @Transactional(readOnly = true)
    suspend fun existParent(id: Long): Boolean = parentRepository.existsById(id)

    @Transactional(readOnly = true)
    suspend fun findByParentUserId(id: Long): ParentDTO =
        ParentDTO(parentRepository
            .findByUserId(id)
            .orElseThrow { UserNotFoundException(id, "존재하지 않는 부모 id 입니다.") }
        )

    @Transactional(readOnly = true)
    suspend fun findByParentUserIdOrNull(id: Long): ParentDTO? {
        val parent = parentRepository
            .findByUserId(id)
            .getOrNull()
        return if (parent != null) ParentDTO(parent) else null
    }

    @Transactional(readOnly = true)
    suspend fun findByParentEntityIdOrNull(id: Long): Parent? =
        parentRepository
            .findById(id)
            .getOrNull()

    @Transactional(readOnly = true)
    suspend fun findByParentEntityId(id: Long): Parent =
       parentRepository
            .findById(id)
            .orElseThrow { UserNotFoundException(id, "존재하지 않는 부모 id 입니다.") }


    @Transactional(readOnly = true)
    suspend fun findByParentId(id: Long): ParentDTO =
        ParentDTO(findByParentEntityId(id))

    @Transactional
    suspend fun deleteByUserId(id : Long) {
        val parent = findByParentUserId(id)
        parentRepository.deleteById(parent.id!!)
        // TODO child와 갖고 있는 모든 연관관계 제거 (TranslateResult의 child를 null로 하거나..)
    }

    @Transactional
    suspend fun findByChildIdOrNull(userId : Long?, childId : Long?) : ChildDTO? {
        if (userId == null)
            return null

        if (childId == null)
            return null

        val parent = findByParentUserIdOrNull(userId)
        return parent?.children?.find { it.id == childId }
    }


    @Transactional
    suspend fun updateProfile(id: Long, parentEditDTO: ParentEditDTO) {
        val existingUser = parentRepository.findByUserId(id).orElseThrow {
            UserNotFoundException(id, "일치하는 사용자가 없습니다")
        }

        existingUser.children.forEachIndexed { index, child ->
            val updatedChildDTO = parentEditDTO.children.getOrNull(index)
            updatedChildDTO?.let {
                child.name = it.name
                child.phone = it.phone
                child.school = it.school
                child.grade = it.grade
                child.className = it.className
                child.gender = it.gender
            }
        }
        parentRepository.save(existingUser)
    }

    //for edit. 만약 parent삭제를 위한 기능이면 이미 profile service에서 remove child를 호출함
    //부모가 수정되어 child가 제거된 상황이라면 remove child를 호출해줘야되는데..
    // TODO 추후 EditService로 분리해서 AutoTranslateService 호출하기.
    @Transactional
    suspend fun deleteChild(parentId : Long, childId: Long) {
        val parent = findByParentEntityIdOrNull(parentId)
        if (parent != null) {
            val findChild = parent.children.find { it.id == childId }
            parent.children.remove(findChild)
            translateManageService.removeChild(childId)
            parentRepository.save(parent)
        }
        else
            log.warn("존재 하지 않는 부모 ID입니다 : {}", parentId)
    }
}