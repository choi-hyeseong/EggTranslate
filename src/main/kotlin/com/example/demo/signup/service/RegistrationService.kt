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
import com.example.demo.user.translator.dto.TranslatorDTO
import com.example.demo.user.translator.repository.TranslatorRepository
import org.springframework.stereotype.Service

@Service
class RegistrationService(
        private val userService: UserService,
        private val parentService: ParentService,
        private val teacherRepository: TeacherRepository,
        private val translatorRepository: TranslatorRepository
) {

    fun registerUser(userDTO: UserDto) : Long {
        return userService.signUp(userDTO)
    }


    fun registerParent(parentDTO: ParentDTO) : ParentDTO {
        val userResult = registerUser(parentDTO.user)
        if (userResult != -1L) {
            parentService.signUp(parentDTO)
            return parentService.findByParentUserId(userResult)
        }
        else
            throw RegistrationFailedException("부모 회원가입에 실패하였습니다.")


    }

    fun registerTeacher(teacherDTO: TeacherDTO) {
        val userDTO = teacherDTO.userDto
        registerUser(userDTO)

        // TeacherDTO를 Teacher 엔티티로 변환
        val teacher = teacherDTO.toEntity()

        teacherRepository.save(teacher)
    }

    fun registerTranslator(translatorDTO: TranslatorDTO) {
        val userDTO = translatorDTO.user
        registerUser(userDTO)

        // TranslatorDTO를 Translator 엔티티로 변환
        val translator = translatorDTO.toEntity()

        translatorRepository.save(translator)
    }
}