package com.example.demo.signup.service

import com.example.demo.signup.exception.RegistrationFailedException
import com.example.demo.user.basic.dto.UserDto
import com.example.demo.user.basic.repository.UserRepository
import com.example.demo.user.basic.service.UserService
import com.example.demo.user.parent.child.entity.Child
import com.example.demo.user.parent.child.repository.ChildRepository
import com.example.demo.user.parent.dto.ParentDTO
import com.example.demo.user.parent.entity.Parent
import com.example.demo.user.parent.repository.ParentRepository
import com.example.demo.user.parent.service.ParentService
import com.example.demo.user.teacher.dto.TeacherDTO
import com.example.demo.user.teacher.repository.TeacherRepository
import com.example.demo.user.teacher.service.TeacherService
import com.example.demo.user.translator.dto.TranslatorDTO
import com.example.demo.user.translator.repository.TranslatorRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
@Transactional
class RegistrationService(
        private val userService: UserService,
        private val parentService: ParentService,
        private val teacherService: TeacherService,
        private val translatorRepository: TranslatorRepository
) {

    fun registerUser(userDTO: UserDto) : Long {
        return userService.signUp(userDTO)
    }

    fun registerParent(parentDTO: ParentDTO) : ParentDTO {
        val userResult = registerUser(parentDTO.user)
        if (userResult == -1L)
            throw RegistrationFailedException("유저 회원가입에 실패하였습니다.")

        val parentResult = parentService.signUp(parentDTO)
        if (parentResult == -1L)
            throw RegistrationFailedException("부모 회원가입에 실패하였습니다.")

        return parentService.findByParentUserId(userResult)
    }

    fun registerTeacher(teacherDTO: TeacherDTO) : TeacherDTO {
        val userResult = registerUser(teacherDTO.userDto) // 등록한 유저의 id
        if (userResult == -1L)
            throw RegistrationFailedException("유저 회원가입에 실패하였습니다.")

        val teacherResult = teacherService.signUp(teacherDTO)
        if (teacherResult == -1L)
            throw RegistrationFailedException("선생 회원가입에 실패하였습니다.")

        return teacherService.findTeacherByUserId(userResult)
    }

    fun registerTranslator(translatorDTO: TranslatorDTO) {
        val userResult = registerUser(translatorDTO.userDto)
        if (userResult == -1L)
            throw RegistrationFailedException("유저 회원가입에 실패하였습니다.")

        val translatorResult = translatorService.signUp(translatorDTO)
        if (translatorResult == -1L)
            throw RegistrationFailedException("선생 회원가입에 실패하였습니다.")

        return translatorService.findTranslatorByUserId(userResult)

    }
}