package com.example.demo.user.teacher.dto

import com.example.demo.user.basic.dto.UserDto

class TeacherResponseDTO (
    val id : Long?,
    val user: Long?,
    val school : String,
    val grade : Int,
    val className : String,
    val course : String?,
    val address : String?,
)