package com.example.demo.user.teacher.dto

import com.example.demo.user.basic.dto.UserDto
import com.example.demo.user.basic.entity.User
import com.example.demo.user.teacher.entity.Teacher
import com.example.demo.user.translator.entity.Translator
import com.example.demo.user.translator.type.TranslatorCategory
import com.example.demo.user.translator.type.TranslatorLevel

class TeacherDTO(
    val school : String,
    val grade : Int,
    val className : String,
    val course : String?,
    val address : String?,
    val userDto: UserDto

) {

    constructor(teacher: Teacher) : this(teacher.school, teacher.grade, teacher.className, teacher.course, teacher.address, UserDto(teacher.user))
    fun toEntity() : Teacher = Teacher(school, grade, className, course, address, userDto.toEntity())
}