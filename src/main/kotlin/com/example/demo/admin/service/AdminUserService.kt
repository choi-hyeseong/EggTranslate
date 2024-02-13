package com.example.demo.admin.service

import com.example.demo.admin.dto.AdminSignUpDTO
import com.example.demo.admin.model.AdminGenerationData
import com.example.demo.common.page.Pageable
import com.example.demo.logger
import com.example.demo.profile.service.ProfileService
import com.example.demo.signup.dto.TranslatorSignUpDTO
import com.example.demo.signup.dto.UserSignUpDTO
import com.example.demo.signup.service.RegistrationService
import com.example.demo.user.basic.dto.UserDto
import com.example.demo.user.basic.dto.UserListItemDTO
import com.example.demo.user.basic.dto.UserResponseDTO
import com.example.demo.user.basic.service.UserService
import com.example.demo.user.basic.type.UserType
import com.example.demo.user.parent.dto.*
import com.example.demo.user.parent.service.ParentService
import com.example.demo.user.teacher.dto.*
import com.example.demo.user.teacher.service.TeacherService
import com.example.demo.user.translator.dto.*
import com.example.demo.user.translator.service.TranslatorService
import kotlinx.coroutines.runBlocking
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import javax.annotation.PostConstruct

@Service
class AdminUserService(
    val userService: UserService,
    val parentService: ParentService,
    val teacherService: TeacherService,
    val translatorService: TranslatorService,
    val registrationService: RegistrationService,
    val profileService: ProfileService,
    private val adminGenerationData: AdminGenerationData
) {

    private val log = logger()

    /**
     * Property에 관리자 자동 생성이 켜져있고, 관리자가 존재하지 않는경우 관리자를 생성함
     */
    @PostConstruct
    @Transactional
    fun handlePostConstruct() {
        runBlocking {
            if (!userService.existByUserType(UserType.ADMIN) && adminGenerationData.enable) //Admin이 존재하지 않을경우
                kotlin.runCatching {
                    createAdmin(adminGenerationData.toAdminDTO())
                }.onSuccess {
                    log.info("관리자 계정이 존재하지 않아 신규 계정을 생성합니다. login id : {}", it.userName)
                }.onFailure {
                    log.warn("관리자 계정 자동 생성에 실패하였습니다. properties의 값을 확인하십시오. error : {}", it.message)
                }
        }
    }

    @Transactional
    suspend fun findUserList(page : Int, amount : Int) : Pageable<UserListItemDTO> {
        return userService.getList(page, amount)
    }

    @Transactional
    suspend fun findUserDetail(id : Long) : UserResponseDTO {
        return userService.getDetail(id)
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
        return parentService.getList(page, amount)
    }

    @Transactional
    suspend fun findParentDetail(id : Long) : ParentResponseDTO {
        return parentService.getDetail(id)
    }

    @Transactional
    suspend fun convertToParent(userId : Long, parentConvertDTO: ParentConvertDTO) : Long? {
        return profileService.convertToParent(userId, parentConvertDTO)
    }

    @Transactional
    suspend fun updateParent(id : Long, updateDTO: ParentUpdateDTO) : ParentDTO {
        return parentService.update(id, updateDTO)
    }

    /*
    * Teacher Part
     */

    @Transactional
    suspend fun findTeacherList(page : Int, amount : Int) : Pageable<TeacherListItemDTO> {
        return teacherService.getList(page, amount)
    }

    @Transactional
    suspend fun findTeacherDetail(id : Long) : TeacherResponseDTO {
        return teacherService.getDetail(id)
    }

    @Transactional
    suspend fun convertToTeacher(userId : Long, teacherConvertDTO: TeacherConvertDTO) : Long? {
        return profileService.convertToTeacher(userId, teacherConvertDTO)
    }

    @Transactional
    suspend fun updateTeacher(id : Long, teacherUpdateDTO: TeacherUpdateDTO) : TeacherDTO {
        return profileService.updateTeacher(id, teacherUpdateDTO)
    }

    /*
    * Translator Part
    */


    @Transactional
    suspend fun findTranslatorList(page : Int, amount : Int) : Pageable<TranslatorListItemDTO> {
        return translatorService.getList(page, amount)
    }

    @Transactional
    suspend fun findTranslatorDetail(id : Long) : TranslatorResponseDTO {
        return translatorService.getDetail(id)
    }

    @Transactional
    suspend fun registerTranslator(translatorSignUpDTO: TranslatorSignUpDTO) : TranslatorDTO {
        return registrationService.registerTranslator(translatorSignUpDTO)
    }

    @Transactional
    suspend fun convertToTranslator(userId : Long, translatorConvertDTO: TranslatorConvertDTO) : Long? {
        return profileService.convertToTranslator(userId, translatorConvertDTO)
    }

    @Transactional
    suspend fun updateTranslator(id : Long, translatorUpdateDTO: TranslatorUpdateDTO) : TranslatorDTO {
        return profileService.updateTranslator(id, translatorUpdateDTO)
    }

    @Transactional
    suspend fun createAdmin(adminSignUpDTO: AdminSignUpDTO) : UserDto {
        val response = registrationService.registerUser(adminSignUpDTO.toUserDTO())
        return userService.getUser(response!!)
    }

    @Transactional
    suspend fun convertAdmin(id : Long) : UserDto {
        return profileService.convertToAdmin(id)
    }

}