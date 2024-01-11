package com.example.demo.profile.controller

import com.example.demo.profile.dto.ParentEditDTO
import com.example.demo.profile.dto.TeacherEditDTO
import com.example.demo.profile.dto.TranslatorEditDTO
import com.example.demo.profile.service.ProfileService
import com.example.demo.user.basic.dto.UserDto
import com.example.demo.user.basic.entity.User
import com.example.demo.user.basic.repository.UserRepository
import com.example.demo.user.parent.dto.ParentDTO
import com.example.demo.user.teacher.dto.TeacherDTO
import com.example.demo.user.translator.dto.TranslatorDTO
import lombok.RequiredArgsConstructor
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
class ProfileController(private val userRepository: UserRepository, private val profileService : ProfileService) {

    @GetMapping("")
    fun retrieveAllUsers() : List<User> {
        return userRepository.findAll()
    }

    @PutMapping("/edit/parent/{id}")
    suspend fun editParentProfile(@PathVariable id : Long, @RequestBody parentEditDTO: ParentEditDTO) : Long{
        profileService.updateParent(id, parentEditDTO)
        return id
    }

    @PutMapping("/edit/teacher/{id}")
    suspend fun editTeacherProfile(@PathVariable id : Long, @RequestBody teacherEditDTO: TeacherEditDTO) : Long{
        profileService.updateTeacher(id, teacherEditDTO)
        return id
    }

    @PutMapping("/edit/translator/{id}")
    suspend fun editTranslatorProfile(@PathVariable id : Long, @RequestBody translatorEditDTO: TranslatorEditDTO) : Long{
        profileService.updateTranslator(id, translatorEditDTO)
        return id
    }

    @DeleteMapping("/delete/{id}")
    suspend fun deleteProfile(@PathVariable id : Long) : Long{
        userRepository.deleteById(id)
        return id
    }

    @GetMapping("/{id}")
    fun retrieveUser(@PathVariable id : Long) : Optional<User> {
        return userRepository.findById(id)
    }
}