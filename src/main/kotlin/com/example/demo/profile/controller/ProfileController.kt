package com.example.demo.profile.controller

import com.example.demo.auth.security.config.getUserOrThrow
import com.example.demo.common.response.Response
import com.example.demo.member.parent.dto.ParentResponseDTO
import com.example.demo.profile.service.ProfileService
import com.example.demo.member.user.repository.UserRepository
import com.example.demo.member.parent.dto.ParentUpdateDTO
import com.example.demo.member.parent.service.ParentService
import com.example.demo.member.teacher.dto.TeacherResponseDTO
import com.example.demo.member.teacher.dto.TeacherUpdateDTO
import com.example.demo.member.teacher.service.TeacherService
import com.example.demo.member.translator.dto.TranslatorResponseDTO
import com.example.demo.member.translator.dto.TranslatorUpdateDTO
import com.example.demo.member.translator.service.TranslatorService
import com.example.demo.member.user.dto.UserResponseDTO
import com.example.demo.member.user.service.UserService
import lombok.RequiredArgsConstructor
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
class ProfileController(
    private val profileService: ProfileService
) {
    @GetMapping("")
    suspend fun getUserProfile(authentication: Authentication) : Response<UserResponseDTO> {
        return Response.ofSuccess(null, profileService.getUserProfile(authentication))
    }

    @GetMapping("/parent")
    suspend fun getParentProfile(authentication: Authentication) : Response<ParentResponseDTO> {
        return Response.ofSuccess(null, profileService.getParentProfile(authentication))
    }

    @GetMapping("/translator")
    suspend fun getTranslatorProfile(authentication: Authentication) : Response<TranslatorResponseDTO> {
        return Response.ofSuccess(null, profileService.getTranslatorProfile(authentication))
    }

    @GetMapping("/teacher")
    suspend fun getTeacherProfile(authentication: Authentication) : Response<TeacherResponseDTO> {
        return Response.ofSuccess(null, profileService.getTeacherProfile(authentication))
    }



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