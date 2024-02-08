package com.example.demo.auth.jwt.dto

data class TokenDTO (
    val type : String,
    val accessToken : String,
    val refreshToken : String
)