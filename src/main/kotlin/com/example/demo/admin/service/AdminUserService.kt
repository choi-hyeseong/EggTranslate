package com.example.demo.admin.service

import com.example.demo.common.page.Pageable
import com.example.demo.common.response.Response
import com.example.demo.profile.service.ProfileService
import com.example.demo.user.basic.dto.UserListItemDTO
import com.example.demo.user.basic.dto.UserResponseDTO
import com.example.demo.user.basic.service.UserService
import com.example.demo.user.basic.type.UserType
import com.example.demo.user.parent.dto.ParentConvertDTO
import com.example.demo.user.parent.dto.ParentListItemDTO
import com.example.demo.user.parent.dto.ParentResponseDTO
import com.example.demo.user.parent.service.ParentService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*

@Service
class AdminUserService(
    val userService: UserService,
    val parentService: ParentService,
    val profileService: ProfileService
) {



    @Transactional
    suspend fun findUserList(page : Int, amount : Int) : Pageable<UserListItemDTO> {
        return userService.getUserList(page, amount)
    }

    @Transactional
    suspend fun findUserDetail(id : Long) : UserResponseDTO {
        return userService.getUser(id).toResponseDTO()
    }

    @Transactional
    suspend fun deleteUser(id : Long, force : Boolean)  {
        profileService.deleteProfile(id, force)
    }

    /*
    *   Parent Part, id는 userId로 고정.
    */
    @Transactional
    suspend fun findParentList(page : Int, amount : Int) : Pageable<ParentListItemDTO> {
        return parentService.getParentList(page, amount)
    }

    @Transactional
    suspend fun findParentDetail(id : Long) : ParentResponseDTO {
        return parentService.findByParentId(id).toResponseDTO()
    }

    @Transactional
    suspend fun convertToParent(userId : Long, parentConvertDTO: ParentConvertDTO) : Long? {
        val user = userService.getUser(userId)
        profileService.deleteSpecific(userId, user.userType) //등록된 다른 데이터 삭제
        userService.updateUserType(userId, UserType.PARENT)
        return parentService.createParent(parentConvertDTO.toParentDTO(user))
    }

    @Transactional
    suspend fun updateParent(@PathVariable id : Long)  {
    }
}