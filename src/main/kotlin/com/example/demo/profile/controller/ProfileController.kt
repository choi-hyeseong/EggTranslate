package com.example.demo.profile.controller

import com.example.demo.profile.service.ProfileService
import com.example.demo.user.basic.dto.UserDto
import com.example.demo.user.basic.entity.User
import com.example.demo.user.basic.repository.UserRepository
import com.example.demo.user.basic.service.UserService
import com.example.demo.user.basic.type.UserType
import com.example.demo.user.parent.dto.ParentDTO
import com.example.demo.user.parent.dto.ParentUpdateDTO
import com.example.demo.user.parent.service.ParentService
import com.example.demo.user.teacher.dto.TeacherDTO
import com.example.demo.user.teacher.dto.TeacherUpdateDTO
import com.example.demo.user.teacher.repository.TeacherRepository
import com.example.demo.user.teacher.service.TeacherService
import com.example.demo.user.translator.dto.TranslatorDTO
import com.example.demo.user.translator.dto.TranslatorUpdateDTO
import com.example.demo.user.translator.service.TranslatorService
import lombok.RequiredArgsConstructor
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
class ProfileController(
    private val userRepository: UserRepository,
    private val profileService: ProfileService
) {

    @PutMapping("/edit/parent/{id}")
    suspend fun editParentProfile(@PathVariable id: Long, @RequestBody parentUpdateDTO: ParentUpdateDTO): Long {
        profileService.updateParent(id, parentUpdateDTO)
        return id
    }

    @PutMapping("/edit/teacher/{id}")
    suspend fun editTeacherProfile(@PathVariable id: Long, @RequestBody teacherUpdateDTO: TeacherUpdateDTO): Long {
        profileService.updateTeacher(id, teacherUpdateDTO)
        return id
    }

    @PutMapping("/edit/translator/{id}")
    suspend fun editTranslatorProfile(@PathVariable id: Long, @RequestBody translatorUpdateDTO: TranslatorUpdateDTO): Long {
        profileService.updateTranslator(id, translatorUpdateDTO)
        return id
    }

    @DeleteMapping("/delete/{id}")
    suspend fun deleteProfile(@PathVariable id: Long) {
        profileService.deleteProfile(id)
    }

}