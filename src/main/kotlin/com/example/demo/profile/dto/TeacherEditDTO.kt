package com.example.demo.profile.dto

import com.example.demo.signup.dto.UserSignUpDTO

class TeacherEditDTO(
        val school : String,
        val grade : Int,
        val className : String,
        val course : String?,
        val address : String?,
        val user: UserEditDTO
) {
}