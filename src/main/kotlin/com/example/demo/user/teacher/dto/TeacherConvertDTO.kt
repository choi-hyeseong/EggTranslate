package com.example.demo.user.teacher.dto

import com.example.demo.user.basic.dto.UserDto

class TeacherConvertDTO (
    val school : String,
    val grade : Int,
    val className : String,
    val course : String?,
    val address : String?,
) {
    fun toTeacherDTO(userDto: UserDto) : TeacherDTO = TeacherDTO(null, school, grade, className, course, address, userDto)
}