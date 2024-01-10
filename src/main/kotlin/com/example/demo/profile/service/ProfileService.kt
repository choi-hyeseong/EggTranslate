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

    suspend fun updateUser(id : Long, userDto: UserDto) {
        userService.updateProfile(id, userDto)
    }

    suspend fun updateParent(id : Long, parentDTO: ParentDTO) {
        //user 정보 업데이트
        val dto = parentDTO.user
        updateUser(id, dto)
        //부모 단독 정보 업데이트
        parentService.updateProfile(id, parentDTO)
    }

    suspend fun updateTeacher(id : Long, teacherDTO: TeacherDTO) {
        //user 정보 업데이트
        val dto = teacherDTO.user
        updateUser(id, dto)
        //선생 단독 정보 업데이트
        teacherService.updateProfile(id, teacherDTO)
    }

    suspend fun updateTranslator (id : Long, translatorDTO: TranslatorDTO) {
        //user 정보 업데이트
        val dto = translatorDTO.user
        updateUser(id, dto)
        //번역가 단독 정보 업데이트
        translatorService.updateProfile(id, translatorDTO)
    }


}