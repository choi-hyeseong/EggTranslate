package com.example.demo.auth.controller

import com.example.demo.auth.jwt.dto.TokenDTO
import com.example.demo.auth.service.UserAuthenticateService
import com.example.demo.user.basic.dto.UserLoginDTO
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class LoginController(private val userAuthenticateService: UserAuthenticateService) {

    @PostMapping("/login")
    suspend fun login(@RequestBody userLoginDTO: UserLoginDTO) : TokenDTO {
        return userAuthenticateService.login(userLoginDTO)
    }

    //logout
}