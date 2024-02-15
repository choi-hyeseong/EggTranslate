package com.example.demo.auth.jwt.dto

import io.swagger.v3.oas.annotations.media.Schema

data class TokenDTO (
    @Schema(name = "type", description = "토큰의 타입입니다.")
    val type : String,
    @Schema(name = "accessToken", description = "리소스 접근에 필요한 토큰입니다.")
    val accessToken : String,
    @Schema(name = "refreshToken", description = "access token 갱신에 필요한 토큰입니다. (현재 미사용)")
    val refreshToken : String
)