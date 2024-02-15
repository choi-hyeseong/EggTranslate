package com.example.demo.signup.dto

import com.example.demo.member.user.type.UserType
import com.example.demo.member.teacher.dto.TeacherDTO

class TeacherSignUpDTO (
        val school : String,
        val grade : Int,
        val className : String,
        val course : String?,
        val address : String?,
        val user: UserSignUpDTO
) {
    fun toTeacherDTO() : TeacherDTO = TeacherDTO(null, school, grade, className, course, address, user.toUserDTO(
        UserType.TEACHER))
}