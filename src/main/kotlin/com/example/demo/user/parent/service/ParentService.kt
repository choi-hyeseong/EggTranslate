package com.example.demo.user.parent.service

import com.example.demo.profile.dto.ChildEditDTO
import com.example.demo.profile.dto.ParentEditDTO
import com.example.demo.user.basic.dto.UserDto
import com.example.demo.user.basic.exception.UserNotFoundException
import com.example.demo.user.parent.child.dto.ChildRequestDto
import com.example.demo.user.parent.dto.ParentDTO
import com.example.demo.user.parent.repository.ParentRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ParentService(private val parentRepository: ParentRepository) {

    @Transactional
    fun signUp(parentDTO: ParentDTO): Long {
        return if (existParent(parentDTO.id))
            -1
        else
            parentRepository.save(parentDTO.toEntity()).id
    }

    @Transactional(readOnly = true)
    fun existParent(id: Long): Boolean = parentRepository.existsById(id)

    @Transactional(readOnly = true)
    fun findByParentUserId(id: Long): ParentDTO =
        ParentDTO(parentRepository
            .findByUserId(id)
            .orElseThrow { UserNotFoundException(id, "존재하지 않는 부모 id 입니다.") }
        )

    @Transactional(readOnly = true)
    fun findByParentId(id: Long): ParentDTO =
        ParentDTO(parentRepository
            .findById(id)
            .orElseThrow { UserNotFoundException(id, "존재하지 않는 부모 id 입니다.") }
        )

    @Transactional
    suspend fun updateProfile(id : Long, parentEditDTO: ParentEditDTO) {
        val existingUser = parentRepository.findByUserId(id).orElseThrow{
            UserNotFoundException(id, "일치하는 사용자가 없습니다")
        }
        existingUser.children = parentEditDTO.children.map(ChildEditDTO::toChildDTO).map { it.toEntity() }.toMutableList()
        // 에러가 여기서 나는 것 같은데...
        // 디버깅해보니까 updateProfile에 들어왔다가 다시 밖으로 나갔다가 다시 안으로 들어오는 것도 이상함. 뭐지 이건?
        parentRepository.save(existingUser) // save 쪽에서 영속성이 깨져서 에러가 나는 것 같은데.. 확인해보니까 id가 잘못 들어가있음. 근데 코드 고치니까 아이디는 제대로 들어가있던데 ?
    }

}