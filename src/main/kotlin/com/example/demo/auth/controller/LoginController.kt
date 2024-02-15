package com.example.demo.auth.controller

import com.example.demo.auth.jwt.dto.TokenDTO
import com.example.demo.auth.service.UserAuthenticateService
import com.example.demo.user.basic.dto.UserLoginDTO
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class LoginController(private val userAuthenticateService: UserAuthenticateService) {

    @Operation(
        summary = "로그인", description = "로그인을 진행합니다", responses = [
            ApiResponse(
                responseCode = "200",
                description = "성공",
                content = [Content(mediaType = "application/json")],
                useReturnTypeSchema = true
            ),
            ApiResponse(
                responseCode = "403",
                description = "실패",
                content = []
            )]
    )
    @PostMapping("/login")
    suspend fun login(@RequestBody userLoginDTO: UserLoginDTO) : TokenDTO {
        return userAuthenticateService.login(userLoginDTO)
    }

    //logout
}