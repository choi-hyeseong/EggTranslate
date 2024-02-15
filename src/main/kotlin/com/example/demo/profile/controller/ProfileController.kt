package com.example.demo.profile.controller

import com.example.demo.profile.service.ProfileService
import com.example.demo.member.user.repository.UserRepository
import com.example.demo.member.parent.dto.ParentUpdateDTO
import com.example.demo.member.teacher.dto.TeacherUpdateDTO
import com.example.demo.member.translator.dto.TranslatorUpdateDTO
import lombok.RequiredArgsConstructor
import org.springframework.web.bind.annotation.*

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