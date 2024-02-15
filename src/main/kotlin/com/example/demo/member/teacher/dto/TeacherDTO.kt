package com.example.demo.member.teacher.dto

import com.example.demo.member.user.dto.UserDto
import com.example.demo.member.user.entity.User
import com.example.demo.member.teacher.entity.Teacher

class TeacherDTO(
    val id : Long?,
    val school : String,
    val grade : Int,
    val className : String,
    val course : String?,
    val address : String?,
    val user: UserDto

) {

    constructor(teacher: Teacher) : this(teacher.id, teacher.school, teacher.grade, teacher.className, teacher.course, teacher.address, UserDto(teacher.user))
    fun toEntity(user : User) : Teacher = Teacher(id, school, grade, className, course, address, user)
    fun toResponseDTO() : TeacherResponseDTO = TeacherResponseDTO(id, user.id, school, grade, className, course, address)
}