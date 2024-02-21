package com.example.demo.profile.service

import com.example.demo.auth.security.config.getUserOrThrow
import com.example.demo.board.service.BoardService
import com.example.demo.common.response.Response
import com.example.demo.docs.service.DocumentService
import com.example.demo.file.service.FileService
import com.example.demo.translate.auto.service.TranslateManageService
import com.example.demo.member.user.dto.UserDto
import com.example.demo.member.user.service.UserService
import com.example.demo.member.user.type.UserType
import com.example.demo.member.heart.service.HeartService
import com.example.demo.member.parent.dto.ParentConvertDTO
import com.example.demo.member.parent.dto.ParentResponseDTO
import com.example.demo.member.parent.dto.ParentUpdateDTO
import com.example.demo.member.parent.service.ParentService
import com.example.demo.member.teacher.dto.TeacherConvertDTO
import com.example.demo.member.teacher.dto.TeacherDTO
import com.example.demo.member.teacher.dto.TeacherResponseDTO
import com.example.demo.member.teacher.dto.TeacherUpdateDTO
import com.example.demo.member.teacher.service.TeacherService
import com.example.demo.member.translator.dto.TranslatorConvertDTO
import com.example.demo.member.translator.dto.TranslatorDTO
import com.example.demo.member.translator.dto.TranslatorResponseDTO
import com.example.demo.member.translator.dto.TranslatorUpdateDTO
import com.example.demo.member.translator.service.TranslatorService
import com.example.demo.member.user.dto.UserResponseDTO
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.GetMapping

@Service
@Transactional
class ProfileService(
    private val userService: UserService,
    private val parentService: ParentService,
    private val teacherService: TeacherService,
    private val documentService: DocumentService,
    private val translatorService: TranslatorService,
    private val translateManageService: TranslateManageService,
    private val fileService: FileService,
    private val heartService: HeartService,
    private val boardService: BoardService
    ) {

    /*
    *   Info part
     */
    @Transactional
    suspend fun getUserProfile(authentication: Authentication) : UserResponseDTO {
        val username = authentication.getUserOrThrow().username
        return userService.findUserByUserName(username).toResponseDTO()
    }

    @Transactional
    suspend fun getParentProfile(authentication: Authentication) : ParentResponseDTO {
        val username = authentication.getUserOrThrow().username
        val user = userService.findUserByUserName(username)
        return parentService.findByParentUserId(user.id!!).toResponseDTO()
    }

    @Transactional
    suspend fun getTranslatorProfile(authentication: Authentication) : TranslatorResponseDTO {
        val username = authentication.getUserOrThrow().username
        val user = userService.findUserByUserName(username)
        return translatorService.findTranslatorByUserId(user.id!!).toResponseDTO()
    }

    @Transactional
    suspend fun getTeacherProfile(authentication: Authentication) : TeacherResponseDTO {
        val username = authentication.getUserOrThrow().username
        val user = userService.findUserByUserName(username)
        return teacherService.findTeacherByUserId(user.id!!).toResponseDTO()
    }


    /*
    * Update Part
    */

    @Transactional
    suspend fun updateParent(id : Long, parentUpdateDTO: ParentUpdateDTO) {
        parentService.update(id, parentUpdateDTO)
    }

    @Transactional
    suspend fun updateTeacher(id : Long, teacherUpdateDTO: TeacherUpdateDTO) : TeacherDTO {
       return teacherService.update(id, teacherUpdateDTO)
    }

    @Transactional
    suspend fun updateTranslator (id : Long, translatorUpdateDTO: TranslatorUpdateDTO) : TranslatorDTO{
        return translatorService.update(id, translatorUpdateDTO)
    }

    /*
    * Convert Part
    */

    @Transactional
    suspend fun convertToParent(userId: Long, parentConvertDTO : ParentConvertDTO) : Long? {
        convertUserType(userId, UserType.PARENT)
        return parentService.convert(userId, parentConvertDTO)
    }

    @Transactional
    suspend fun convertToTeacher(userId: Long, teacherConvertDTO: TeacherConvertDTO) : Long? {
        convertUserType(userId, UserType.TEACHER)
        return teacherService.convert(userId, teacherConvertDTO)
    }

    @Transactional
    suspend fun convertToTranslator(userId: Long, translatorConvertDTO: TranslatorConvertDTO) : Long? {
        convertUserType(userId, UserType.TRANSLATOR)
        return translatorService.convert(userId, translatorConvertDTO)
    }

    @Transactional
    suspend fun convertToAdmin(userId: Long) : UserDto {
        convertUserType(userId, UserType.ADMIN)
        return userService.getUser(userId)
    }

    @Transactional
    suspend fun convertUserType(userId: Long, userType: UserType) {
        val user = userService.getUser(userId)
        deleteSpecific(userId, user.userType) //등록된 다른 데이터 삭제
        userService.updateUserType(userId, userType)
    }

    /*
    * Delete Part
    */

    @Transactional
    suspend fun deleteProfile(id : Long, force : Boolean) {
        val user = userService.getUser(id)
        if (!force)
            deleteSpecific(id, user.userType) //강제로 유저 데이터 지우는게 아니라면 각 유저별 타입 데이터 지움
        deleteUser(user.id!!)
    }

    @Transactional
    suspend fun deleteProfile(id : Long) {
        deleteProfile(id, false)
    }

    @Transactional
    suspend fun deleteSpecific(id : Long, userType: UserType) {
        when(userType) {
            //support (UserType)해서 깔끔하게 하는것도 좋을듯
            UserType.TEACHER -> teacherService.deleteByUserId(id)
            UserType.TRANSLATOR -> deleteTranslator(id)
            UserType.PARENT -> deleteParent(id)
            else -> {} //구현 없음.
        }
    }

    @Transactional
    suspend fun deleteTranslator(id : Long) {
        val translator = translatorService.findTranslatorByUserId(id)
        translateManageService.removeTranslatorHistory(translator.id!!)
        heartService.removeTranslatorHeart(id)
        translatorService.delete(translator.id)
    }

    @Transactional
    suspend fun deleteUser(id : Long) {
        translateManageService.deleteUserResult(id)
        heartService.removeUserHeart(id)
        fileService.deleteFileByUserId(id)
        boardService.deleteAllBoardByUserId(id) //보드와 연관된 데이터 다 지우고 해야 외래키 끊어짐
        documentService.deleteAllDocumentByUserId(id)
        userService.deleteById(id)
    }

    @Transactional
    suspend fun deleteParent(userId : Long) {
        translateManageService.removeAllChild(parentService.findByParentUserId(userId).children.map { it.id!! })
        parentService.deleteByUserId(userId)
    }
}