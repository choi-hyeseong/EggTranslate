package com.example.demo.profile.service

import com.example.demo.user.basic.dto.UserDto
import com.example.demo.user.basic.repository.UserRepository
import com.example.demo.user.basic.service.UserService
import com.example.demo.user.parent.dto.ParentDTO
import com.example.demo.user.parent.repository.ParentRepository
import com.example.demo.user.parent.service.ParentService
import com.example.demo.user.teacher.dto.TeacherDTO
import com.example.demo.user.teacher.repository.TeacherRepository
import com.example.demo.user.teacher.service.TeacherService
import com.example.demo.user.translator.dto.TranslatorDTO
import com.example.demo.user.translator.repository.TranslatorRepository
import com.example.demo.user.translator.service.TranslatorService
import org.springframework.stereotype.Service

@Service
class ProfileService(
        private val userService: UserService,
        private val parentService: ParentService,
        private val teacherService: TeacherService,
        private val translatorService: TranslatorService) {

    fun updateUser(id : Long, userDto: UserDto) {

    }

    fun updateParent(id : Long, parentDTO: ParentDTO) {
        val parent =
    }

    fun updateTeacher(id : Long, teacherDTO: TeacherDTO) {

    }

    fun updateTranslator (id : Long, translatorDTO: TranslatorDTO) {

    }


}