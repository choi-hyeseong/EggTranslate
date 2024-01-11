package com.example.demo.signup.dto

import com.example.demo.user.basic.dto.UserDto
import com.example.demo.user.basic.type.UserType
import com.example.demo.user.teacher.dto.TeacherDTO
import com.example.demo.user.translator.dto.TranslatorDTO
import com.example.demo.user.translator.type.TranslatorCategory
import com.example.demo.user.translator.type.TranslatorLevel

class TranslatorSignUpDTO(
        val career : Int,
        val level : TranslatorLevel,
        val user : UserSignUpDTO,
        val certificates : MutableList<String>,
        val categories : MutableList<TranslatorCategory>
) {
    fun toTranslatorDTO() : TranslatorDTO = TranslatorDTO(-1, career, level, user.toUserDTO(UserType.TRANSLATOR), certificates, categories)
}