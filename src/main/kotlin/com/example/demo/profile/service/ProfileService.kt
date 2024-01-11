package com.example.demo.profile.service

import com.example.demo.profile.dto.ParentEditDTO
import com.example.demo.profile.dto.TeacherEditDTO
import com.example.demo.profile.dto.TranslatorEditDTO
import com.example.demo.profile.dto.UserEditDTO
import com.example.demo.user.basic.service.UserService
import com.example.demo.user.parent.service.ParentService
import com.example.demo.user.teacher.service.TeacherService
import com.example.demo.user.translator.service.TranslatorService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class ProfileService(
        private val userService: UserService,
        private val parentService: ParentService,
        private val teacherService: TeacherService,
        private val translatorService: TranslatorService) {

    suspend fun updateUser(id : Long, userEditDto: UserEditDTO) {
        userService.updateProfile(id, userEditDto)
    }

    suspend fun updateParent(id : Long, parentEditDTO: ParentEditDTO) {
        //user 정보 업데이트
        val dto = parentEditDTO.user
        updateUser(id, dto)
        //부모 단독 정보 업데이트
        //parentService.updateProfile(id, parentEditDTO)
    }

    suspend fun updateTeacher(id : Long, teacherEditDTO: TeacherEditDTO) {
        //user 정보 업데이트
        val dto = teacherEditDTO.user
        updateUser(id, dto)
        //선생 단독 정보 업데이트
        teacherService.updateProfile(id, teacherEditDTO)
    }

    suspend fun updateTranslator (id : Long, translatorEditDTO: TranslatorEditDTO) {
        //user 정보 업데이트
        val dto = translatorEditDTO.user
        updateUser(id, dto)
        //번역가 단독 정보 업데이트
        translatorService.updateProfile(id, translatorEditDTO)
    }
}