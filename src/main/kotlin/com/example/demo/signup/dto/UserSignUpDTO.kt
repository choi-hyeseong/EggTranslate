package com.example.demo.signup.dto

import com.example.demo.member.user.dto.UserDto
import com.example.demo.member.user.type.UserType

class UserSignUpDTO(
        val userName: String,
        val password: String,
        val name: String,
        val phone: String,
        val email: String,
        val languages: MutableList<String>,
//        val userType: UserType
) {
    fun toUserDTO(userType: UserType): UserDto = UserDto(null, userName, password, name, phone, email, languages, userType)

}