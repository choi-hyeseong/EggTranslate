package com.example.demo.signup.dto

import com.example.demo.user.basic.dto.UserDto
import com.example.demo.user.parent.dto.ParentDTO
import com.example.demo.user.teacher.dto.TeacherDTO

class TeacherSignUpDTO (
        val school : String,
        val grade : Int,
        val className : String,
        val course : String?,
        val address : String?,
        val user: UserSignUpDTO
) {
    fun toTeacherDTO() : TeacherDTO = TeacherDTO(-1, school, grade, className, course, address, user.toUserDTO())
}