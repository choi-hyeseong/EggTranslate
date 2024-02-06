package com.example.demo.user.parent.service

import com.example.demo.user.basic.data.DataFetcher
import com.example.demo.common.page.Pageable
import com.example.demo.convertOrNull
import com.example.demo.logger
import com.example.demo.translate.auto.service.TranslateManageService
import com.example.demo.user.basic.exception.UserNotFoundException
import com.example.demo.user.basic.service.UserService
import com.example.demo.user.parent.child.dto.ChildDTO
import com.example.demo.user.parent.child.entity.Child
import com.example.demo.user.parent.dto.ParentDTO
import com.example.demo.user.parent.dto.ParentListItemDTO
import com.example.demo.user.parent.dto.ParentResponseDTO
import com.example.demo.user.parent.dto.ParentUpdateDTO
import com.example.demo.user.parent.entity.Parent
import com.example.demo.user.parent.repository.ParentRepository
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import kotlin.jvm.optionals.getOrNull
import kotlin.math.max

@Service
class ParentService(
    private val userService: UserService,
    private val parentRepository: ParentRepository,
    private val translateManageService: TranslateManageService
) : DataFetcher<ParentListItemDTO, ParentResponseDTO> {

    val log = logger()

    @Transactional
    suspend fun createParent(parentDTO: ParentDTO): Long? {
        return if (existParentByUserId(parentDTO.user.id!!))
            null
        else {
            val user = userService.getUserEntity(parentDTO.user.id!!)
            parentRepository.save(parentDTO.toEntity(user, parentDTO.children.map { it.toEntity() }.toMutableList())).id
        }
    }

    @Transactional(readOnly = true)
    suspend fun existParentByUserId(userId: Long): Boolean = parentRepository.existsByUserId(userId)

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
        return parent.convertOrNull { ParentDTO(it) }
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
    suspend fun updateParent(id : Long, parentUpdateDTO: ParentUpdateDTO) {
        val parent = findByParentEntityId(id)
        userService.updateUser(id, parentUpdateDTO.user) //유저 업데이트
        updateChild(parent, parentUpdateDTO.children)
        parentRepository.save(parent)

    }

    @Transactional
    suspend fun updateChild(parent: Parent, children: List<ChildDTO>) {
        val requestChild =
        //child의 id가 null인경우 -> 새로 추가되는 자식 (Insert)
            //기존 자식의 id랑 일치하는 id (Update) -> 기존 자식 업데이트. 즉 다른 부모의 자식이랑 연결되지 않게 하기 위함
            children.filter { child -> child.id == null || parent.children.any { it.id == child.id } }
                .map { it.toEntity() }.toMutableList()
        //제거되는 자식 확인
        //부모의 현재 자식 루프 -> 현재 자식이 새로 업데이트 되는 자식에 포함되지 않았다면 삭제.
        val filteredChild = parent.children.filter { child -> requestChild.all { child.id != it.id } }
        deleteChild(parent, filteredChild)
        parent.children.let { //list 강제로 set해버리면 manage 안됨
            it.clear()
            it.addAll(requestChild)
        }
    }

//    // TODO FIX
//    @Transactional
//    suspend fun updateProfile(id: Long, parentEditDTO: ParentEditDTO) {
//        val existingUser = parentRepository.findByUserId(id).orElseThrow {
//            UserNotFoundException(id, "일치하는 사용자가 없습니다")
//        }
//
//        existingUser.children.forEachIndexed { index, child ->
//            val updatedChildDTO = parentEditDTO.children.getOrNull(index)
//            updatedChildDTO?.let {
//                child.name = it.name
//                child.phone = it.phone
//                child.school = it.school
//                child.grade = it.grade
//                child.className = it.className
//                child.gender = it.gender
//            }
//        }
//        parentRepository.save(existingUser)
//    }

    @Transactional
    suspend fun deleteChild(parent: Parent, children: List<Child>) {
        children.forEach { translateManageService.removeChild(it.id!!) }
    }

    @Transactional
    override suspend fun getList(page: Int, amount: Int): Pageable<ParentListItemDTO> {
        val pageUser = parentRepository.findAll(PageRequest.of(page, amount))
        return Pageable(page, max(0, pageUser.totalPages - 1), pageUser.content.map { ParentListItemDTO(it) })
    }

    @Transactional
    override suspend fun getDetail(id: Long): ParentResponseDTO {
       return findByParentUserId(id).toResponseDTO()
    }
}