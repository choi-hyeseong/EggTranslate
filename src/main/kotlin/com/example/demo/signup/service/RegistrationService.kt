package com.example.demo.signup.service

import com.example.demo.signup.dto.ParentSignUpDTO
import com.example.demo.signup.exception.RegistrationFailedException
import com.example.demo.user.basic.dto.UserDto
import com.example.demo.user.basic.service.UserService
import com.example.demo.user.parent.dto.ParentDTO
import com.example.demo.user.parent.service.ParentService
import com.example.demo.user.teacher.dto.TeacherDTO
import com.example.demo.user.teacher.service.TeacherService
import com.example.demo.user.translator.dto.TranslatorDTO
import com.example.demo.user.translator.service.TranslatorService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class RegistrationService(
        private val userService: UserService,
        private val parentService: ParentService,
        private val teacherService: TeacherService,
        private val translatorService: TranslatorService
) {
    @Transactional
    fun registerUser(userDTO: UserDto) : Long {
        return userService.signUp(userDTO)
    }

    @Transactional
    fun registerParent(parentDTO: ParentSignUpDTO) : ParentDTO {
        val dto = parentDTO.toParentDTO()
        val userResult = registerUser(dto.user)
        if (userResult == -1L)
            throw RegistrationFailedException("유저 회원가입에 실패하였습니다.")

        val parentResult = parentService.signUp(dto.apply { user.id = userResult })
        if (parentResult == -1L)
            throw RegistrationFailedException("부모 회원가입에 실패하였습니다.")

        return parentService.findByParentUserId(userResult)
    }

    @Transactional
    fun registerTeacher(teacherDTO: TeacherDTO) : TeacherDTO {
        val userResult = registerUser(teacherDTO.user)
        if (userResult == -1L)
            throw RegistrationFailedException("유저 회원가입에 실패하였습니다.")

        val teacherResult = teacherService.signUp(teacherDTO.apply { user.id = userResult })
        if (teacherResult == -1L)
            throw RegistrationFailedException("선생 회원가입에 실패하였습니다.")

        return teacherService.findTeacherByUserId(userResult)
    }

    fun registerTranslator(translatorDTO: TranslatorDTO) : TranslatorDTO {
        val userResult = registerUser(translatorDTO.user)
        if (userResult == -1L)
            throw RegistrationFailedException("유저 회원가입에 실패하였습니다.")

        val translatorResult = translatorService.signUp(translatorDTO)
        if (translatorResult == -1L)
            throw RegistrationFailedException("선생 회원가입에 실패하였습니다.")

        return translatorService.findTranslatorByUserId(userResult)

    }
}