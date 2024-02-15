package com.example.demo.member.teacher.dto

import com.example.demo.member.user.dto.UserUpdateDTO

class TeacherUpdateDTO (
    val school : String?,
    val grade : Int?,
    val className : String?,
    val course : String?,
    val address : String?,
    val user : UserUpdateDTO?,
)