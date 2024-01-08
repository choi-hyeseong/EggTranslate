package com.example.demo.signup.service

import com.example.demo.user.basic.dto.UserDto
import com.example.demo.user.basic.repository.UserRepository
import com.example.demo.user.parent.child.repository.ChildRepository
import com.example.demo.user.parent.dto.ParentDTO
import com.example.demo.user.parent.repository.ParentRepository
import com.example.demo.user.teacher.dto.TeacherDTO
import com.example.demo.user.teacher.repository.TeacherRepository
import com.example.demo.user.translator.repository.TranslatorRepository
import org.springframework.stereotype.Service

@Service
class RegistrationService(
        private val userRepository: UserRepository,
        private val parentRepository: ParentRepository,
        private val childRepository: ChildRepository,
        private val teacherRepository: TeacherRepository,
        private val translatorRepository: TranslatorRepository
) {

    fun registerUser(userDTO: UserDto) {
        val user = userDTO.toEntity(
                userDTO.id, userDTO.name, userDTO.password,userDTO.phone,userDTO.email, userDTO.languages
        )

        userRepository.save(user)
    }

    fun registerParent(parentDTO: ParentDTO) {
        val userDTO = parentDTO.userDTO
        registerUser(userDTO)

        val parent = Parent(/* 부모에 대한 정보 설정 */)
        parent.user = userRepository.findByUsername(userDTO.username)
        parentRepository.save(parent)

        val childDTO = parentDTO.childDTO
        val child = Child(childName = childDTO.childName, /* 자식에 대한 정보 설정 */)
        child.parent = parent
        childRepository.save(child)
    }

    fun registerTeacher(teacherDTO: TeacherDTO) {
        val userDTO = teacherDTO.userDTO
        registerUser(userDTO)

        val teacher = Teacher(/* 추가적인 선생님 정보 설정 */)
        teacher.user = userRepository.findByUsername(userDTO.username)
        teacherRepository.save(teacher)
    }

    fun registerTranslator(translatorDTO: TranslatorDTO) {
        val userDTO = translatorDTO.userDTO
        registerUser(userDTO)

        val translator = Translator(/* 추가적인 번역가 정보 설정 */)
        translator.user = userRepository.findByUsername(userDTO.username)
        translatorRepository.save(translator)
    }
}