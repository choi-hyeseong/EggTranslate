package com.example.demo.user.teacher.dto

import com.example.demo.user.teacher.entity.Teacher
import com.example.demo.user.translator.entity.Translator
import com.example.demo.user.translator.type.TranslatorCategory
import com.example.demo.user.translator.type.TranslatorLevel

class TeacherDTO(
    val userId : String,
    val password : String,
    val name : String,
    val phone : String,
    val email : String?,
    val language : List<String>,
    val school : String,
    val grade : Int,
    val className : String,
    val course : String?,
    val address : String?

) {
    fun toEntity() : Teacher = Teacher(userId, password, name, phone, email, language, school, grade, className, course, address)
}