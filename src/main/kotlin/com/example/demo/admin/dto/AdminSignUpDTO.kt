package com.example.demo.admin.dto

import com.example.demo.member.user.dto.UserDto
import com.example.demo.member.user.type.UserType

class AdminSignUpDTO (
    val userName : String,
    val password : String,
    val name : String,
    val email : String
) {
    fun toUserDTO() : UserDto = UserDto(null, userName, password, name, "", email, mutableListOf(), UserType.ADMIN)
}