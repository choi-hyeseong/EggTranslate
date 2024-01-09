package com.example.demo.signup.service

import com.example.demo.user.basic.dto.UserDto
import com.example.demo.user.basic.repository.UserRepository
import com.example.demo.user.parent.child.entity.Child
import com.example.demo.user.parent.child.repository.ChildRepository
import com.example.demo.user.parent.dto.ParentDTO
import com.example.demo.user.parent.entity.Parent
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
                userDTO.id, userDTO.name, userDTO.password,userDTO.phone,userDTO.email, userDTO.languages, userDTO.userType
        )
        userRepository.save(user)
    }

    fun registerParent(parentDTO: ParentDTO) {
        val userDTO = parentDTO.user
        registerUser(userDTO)

//        parent.user = userRepository.findByUsername(userDTO.username)
//        parentRepository.save(parent)

        val parent = Parent(user = userRepository.findByUsername(userDTO.username))
        // 부모의 children 필드가 null인 경우 빈 리스트로 초기화
        if (parent.children == null) {
            parent.children = mutableListOf()
        }

        parentRepository.save(parent)
        val childDTO = parentDTO.children // MutableList<ChildRequestDto>
        val children : MutableList<Child>
        for(i in 0 until childDTO.size){
            Parent(childDTO.map { it.toEntity(it.name, it.phone, it.school, it.grade, it.className, it.gender) }.toMutableList(), userDTO.toEntity(
                    userDTO.id, userDTO.name, userDTO.password,userDTO.phone,userDTO.email, userDTO.languages, userDTO.userType
            ))
        }

        val parent = Parent(childDTO, parentDTO.user)))
        childRepository.save(children)
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