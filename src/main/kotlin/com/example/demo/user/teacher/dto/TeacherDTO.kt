package com.example.demo.user.teacher.dto

import com.example.demo.user.basic.dto.UserDto
import com.example.demo.user.teacher.entity.Teacher

class TeacherDTO(
    val id : Long = -1,
    val school : String,
    val grade : Int,
    val className : String,
    val course : String?,
    val address : String?,
    val user: UserDto

) {

    constructor(teacher: Teacher) : this(teacher.id, teacher.school, teacher.grade, teacher.className, teacher.course, teacher.address, UserDto(teacher.user))
    fun toEntity() : Teacher = Teacher(id, school, grade, className, course, address, user.toEntity())
}