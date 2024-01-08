package com.example.demo.signup

import com.example.demo.user.basic.dto.UserDto
import com.example.demo.user.basic.entity.User
import com.example.demo.user.basic.repository.UserRepository
import lombok.RequiredArgsConstructor
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequiredArgsConstructor
@RequestMapping("api/register")
class RegisterController(private val userRepository: UserRepository, private val registerService: RegisterService) {

    @PostMapping("/signup")
    suspend fun createUser(@RequestBody userDto: UserDto) : User {
        val user = userDto.toEntity()
        return userRepository.save(user);
    }

}