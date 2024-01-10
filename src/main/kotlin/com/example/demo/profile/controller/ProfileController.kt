package com.example.demo.profile.controller

import com.example.demo.profile.service.ProfileService
import com.example.demo.user.basic.dto.UserDto
import com.example.demo.user.basic.entity.User
import com.example.demo.user.basic.repository.UserRepository
import com.example.demo.user.parent.dto.ParentDTO
import com.example.demo.user.teacher.dto.TeacherDTO
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
    suspend fun editParentProfile(@PathVariable id : Long, @RequestBody parentDTO: ParentDTO) : Long{
        profileService.updateParent(id, parentDTO)
        return id
    }

    @PutMapping("/edit/teacher/{id}")
    suspend fun editTeacherProfile(@PathVariable id : Long, @RequestBody teacherDTO: TeacherDTO) : Long{
        profileService.updateTeacher(id, teacherDTO)
        return id
    }

    @PutMapping("/edit/translator/{id}")
    suspend fun editTranslatorProfile(@RequestBody parentDTO: ParentDTO) : Long{
        profileService.updateTranslator(id, translatorDTO)
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