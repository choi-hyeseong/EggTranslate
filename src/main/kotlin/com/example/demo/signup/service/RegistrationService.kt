package com.example.demo.signup.service

import com.example.demo.signup.dto.ParentSignUpDTO
import com.example.demo.signup.dto.TeacherSignUpDTO
import com.example.demo.signup.dto.TranslatorSignUpDTO
import com.example.demo.signup.exception.RegistrationFailedException
import com.example.demo.signup.validation.SignUpValid
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

    fun registerUser(userDTO: UserDto) : Long {
        return userService.signUp(userDTO)
    }

    fun registerParent(parentDTO: ParentSignUpDTO) : ParentDTO {
        val dto = parentDTO.toParentDTO() // ParentDTO 반환
        val userResult = registerUser(dto.user) // dto.user -> UserDTO
        if (userResult == -1L)
            throw RegistrationFailedException("유저 회원가입에 실패하였습니다.")

        val parentResult = parentService.signUp(dto.apply {user.id = userResult})
        if (parentResult == -1L)
            throw RegistrationFailedException("부모 회원가입에 실패하였습니다.")

        return parentService.findByParentUserId(userResult)
    }

    fun registerTeacher(teacherDTO: TeacherSignUpDTO) : TeacherDTO {
        val dto = teacherDTO.toTeacherDTO() // teacherDTO
        val userResult = registerUser(dto.user) // 등록한 유저의 id
        if (userResult == -1L)
            throw RegistrationFailedException("유저 회원가입에 실패하였습니다.")

        val teacherResult = teacherService.signUp(dto.apply { user.id = userResult})
        if (teacherResult == -1L)
            throw RegistrationFailedException("선생 회원가입에 실패하였습니다.")

        return teacherService.findTeacherByUserId(userResult)
    }

    fun registerTranslator(translatorDTO: TranslatorSignUpDTO) : TranslatorDTO {
        val dto = translatorDTO.toTranslatorDTO() // translatorDTO 반환
        val userResult = registerUser(dto.user) // translator의 user (UserDTO타입)
        if (userResult == -1L)
            throw RegistrationFailedException("유저 회원가입에 실패하였습니다.")

        val translatorResult = translatorService.signUp(dto.apply { user.id = userResult})
        if (translatorResult == -1L)
            throw RegistrationFailedException("번역가 회원가입에 실패하였습니다.")

        return translatorService.findTranslatorByUserId(userResult)

    }
}