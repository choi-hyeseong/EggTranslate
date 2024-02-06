package com.example.demo.user.teacher.dto

import com.example.demo.user.basic.dto.UserUpdateDTO

class TeacherUpdateDTO (
    val school : String?,
    val grade : Int?,
    val className : String?,
    val course : String?,
    val address : String?,
    val user : UserUpdateDTO?,
)