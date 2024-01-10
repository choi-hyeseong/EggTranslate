package com.example.demo.login.controller

import com.example.demo.login.dto.LoginRequestDTO
import com.example.demo.login.dto.LoginResponseDTO
import com.example.demo.login.service.AuthService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/auth")
class AuthController(
        private val authService: AuthService
) {

    @PostMapping("/login")
    fun login(@RequestBody loginRequest: LoginRequestDTO): ResponseEntity<*> {
        val username = loginRequest.username
        val password = loginRequest.password

        // 사용자 인증
        if (authService.authenticate(username, password)) {
            // 로그인 성공 시 토큰 생성 (여기서는 간단하게 username을 토큰으로 사용)
            val token = username

            // 토큰을 클라이언트에게 반환
            val response = LoginResponseDTO(token)
            return ResponseEntity.ok(response)
        }

        // 인증 실패 시 에러 응답
        return ResponseEntity.status(401).body("Authentication failed")
    }
}