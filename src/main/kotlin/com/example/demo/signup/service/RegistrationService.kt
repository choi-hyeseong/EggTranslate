package com.example.demo.signup.service

import com.example.demo.user.basic.dto.UserDto
import com.example.demo.user.basic.repository.UserRepository
import com.example.demo.user.basic.service.UserService
import com.example.demo.user.parent.child.entity.Child
import com.example.demo.user.parent.child.repository.ChildRepository
import com.example.demo.user.parent.dto.ParentDTO
import com.example.demo.user.parent.entity.Parent
import com.example.demo.user.parent.repository.ParentRepository
import com.example.demo.user.teacher.dto.TeacherDTO
import com.example.demo.user.teacher.repository.TeacherRepository
import com.example.demo.user.translator.dto.TranslatorDTO
import com.example.demo.user.translator.repository.TranslatorRepository
import org.springframework.stereotype.Service

@Service
class RegistrationService(
        private val userService: UserService,
        private val parentRepository: ParentRepository,
        private val childRepository: ChildRepository,
        private val teacherRepository: TeacherRepository,
        private val translatorRepository: TranslatorRepository
) {

    fun registerUser(userDTO: UserDto) : Boolean {
        return userService.signUp(userDTO)
    }

    fun registerParent(parentDTO: ParentDTO) {
        val userDTO = parentDTO.user
        registerUser(userDTO)

        // ParentDTO를 Parent 엔티티로 변환
        val parent = parentDTO.toEntity()

        // 부모 엔티티에 자식들 추가
        parentDTO.children.forEach { childDTO ->
            // ChildDTO를 Child 엔티티로 변환
            val child = childDTO.toEntity()

            // 자식 엔티티를 저장
            childRepository.save(child)

            // 부모 엔티티에 자식 추가
            parent.children.add(child)
        }

        // 부모 엔티티를 저장
        parentRepository.save(parent)
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