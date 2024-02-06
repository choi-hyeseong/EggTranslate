package com.example.demo.admin.service

import com.example.demo.common.page.Pageable
import com.example.demo.profile.service.ProfileService
import com.example.demo.user.basic.dto.UserListItemDTO
import com.example.demo.user.basic.dto.UserResponseDTO
import com.example.demo.user.basic.service.UserService
import com.example.demo.user.basic.type.UserType
import com.example.demo.user.parent.dto.*
import com.example.demo.user.parent.service.ParentService
import com.example.demo.user.teacher.dto.*
import com.example.demo.user.teacher.service.TeacherService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AdminUserService(
    val userService: UserService,
    val parentService: ParentService,
    val teacherService: TeacherService,
    val profileService: ProfileService,
) {

    //원래는 findUserList랑 findUserDetail을 기준으로 인터페이스를 구성해 묶을려고 했음.
    //근데, 이걸 묶기에는 dto를 추상화 해야하는데, 응답 데이터를 추상화 할 수 있나? Any로도 할 수 있긴 하지만, 좋지 않은 방법임.
    //사실상 잘 활용하는 방법이 해당 인터페이스를 구성하고, List<인터페이스>로 받아와 support할경우 반환하는 방식임. - 다형성 이용
    //응답의 추상화 때문에 음...

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
        return profileService.convertToParent(userId, parentConvertDTO)
    }

    @Transactional
    suspend fun updateParent(id : Long, updateDTO: ParentUpdateDTO) : ParentDTO {
        profileService.updateParent(id, updateDTO)
        return parentService.findByParentId(id)
    }

    /*
    * Teacher Part
     */

    @Transactional
    suspend fun findTeacherList(page : Int, amount : Int) : Pageable<TeacherListItemDTO> {
        return teacherService.findTeacherList(page, amount)
    }

    @Transactional
    suspend fun findTeacherDetail(id : Long) : TeacherResponseDTO {
        return teacherService.findTeacherByUserId(id).toResponseDTO()
    }

    @Transactional
    suspend fun convertToTeacher(userId : Long, teacherConvertDTO: TeacherConvertDTO) : Long? {
        return profileService.convertToTeacher(userId, teacherConvertDTO)
    }

    @Transactional
    suspend fun updateTeacher(id : Long, teacherUpdateDTO: TeacherUpdateDTO) : TeacherDTO {
        profileService.updateTeacher(id, teacherUpdateDTO)
        return teacherService.findTeacherByUserId(id)
    }

}