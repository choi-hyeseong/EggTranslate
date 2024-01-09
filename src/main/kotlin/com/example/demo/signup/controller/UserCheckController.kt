package com.example.demo.signup.controller

import com.example.demo.user.basic.entity.User
import com.example.demo.user.basic.repository.UserRepository
import lombok.RequiredArgsConstructor
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequiredArgsConstructor
class UserCheckController (private val userRepository: UserRepository){
    @GetMapping("/api/users")
    suspend fun showUsers() : List<User> {
        return userRepository.findAll()
    }
}