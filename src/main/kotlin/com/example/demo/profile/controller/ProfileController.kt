package com.example.demo.profile.controller

import com.example.demo.user.basic.entity.User
import com.example.demo.user.basic.repository.UserRepository
import lombok.RequiredArgsConstructor
import org.springframework.web.bind.annotation.*

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
class ProfileController(private val userRepository: UserRepository) {

    @GetMapping("")
    fun retrieveAllUsers() : List<User> {
        return userRepository.findAll()
    }

    @PutMapping("/edit")
    suspend fun changeProfile() {

    }

    @DeleteMapping("/delete/{id}")
    suspend fun deleteProfile(@PathVariable id : Long) : Long{
        userRepository.deleteById(id)
        return id
    }

    @GetMapping("/{id}")
    fun retrieveUser(@PathVariable id : Long) : User{
        return userRepository.findOne(id)
    }
}