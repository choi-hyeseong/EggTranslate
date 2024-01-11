package com.example.demo.profile.dto

import com.example.demo.user.basic.type.UserType

class UserEditDTO(
        val userName: String,
        val password: String,
        val name: String,
        val phone: String,
        val email: String?,
        val languages: MutableList<String>,
        val userType: UserType
) {
}