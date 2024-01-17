package com.example.demo.user.parent.service

import com.example.demo.profile.dto.ParentEditDTO
import com.example.demo.user.basic.exception.UserNotFoundException
import com.example.demo.user.basic.service.UserService
import com.example.demo.user.parent.child.dto.ChildDTO
import com.example.demo.user.parent.dto.ParentDTO
import com.example.demo.user.parent.repository.ParentRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import kotlin.jvm.optionals.getOrNull

@Service
class ParentService(
    private val userService: UserService,
    private val parentRepository: ParentRepository
) {

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
            .findByMemberId(id)
            .orElseThrow { UserNotFoundException(id, "존재하지 않는 부모 id 입니다.") }
        )

    @Transactional(readOnly = true)
    suspend fun findByParentUserIdOrNull(id: Long): ParentDTO? {
        val parent = parentRepository
            .findByMemberId(id)
            .getOrNull()
        return if (parent != null) ParentDTO(parent) else null
    }



    @Transactional(readOnly = true)
    suspend fun findByParentId(id: Long): ParentDTO =
        ParentDTO(parentRepository
            .findById(id)
            .orElseThrow { UserNotFoundException(id, "존재하지 않는 부모 id 입니다.") }
        )

    @Transactional
    suspend fun deleteByUserId(id : Long) {
        val parent = findByParentUserId(id)
        parentRepository.deleteById(parent.id!!)
        // TODO child와 갖고 있는 모든 연관관계 제거 (TranslateResult의 child를 null로 하거나..)
    }

    @Transactional
    suspend fun findByChildIdOrNull(userId : Long, childId : Long) : ChildDTO? {
        val parent = findByParentUserIdOrNull(userId)
        return parent?.children?.find { it.id == childId }
    }
    @Transactional
    suspend fun updateProfile(id: Long, parentEditDTO: ParentEditDTO) {
        val existingUser = parentRepository.findByMemberId(id).orElseThrow {
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
}