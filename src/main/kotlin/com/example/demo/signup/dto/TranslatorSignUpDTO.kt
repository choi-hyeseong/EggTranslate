package com.example.demo.signup.dto

import com.example.demo.member.user.type.UserType
import com.example.demo.member.translator.dto.TranslatorDTO
import com.example.demo.member.translator.type.TranslatorCategory
import com.example.demo.member.translator.type.TranslatorLevel

class TranslatorSignUpDTO(
        val career : Int,
        val level : TranslatorLevel,
        val user : UserSignUpDTO,
        val certificates : MutableList<String>,
        val categories : MutableList<TranslatorCategory>
) {
    fun toTranslatorDTO() : TranslatorDTO = TranslatorDTO(null, career, level, user.toUserDTO(UserType.TRANSLATOR), certificates, categories)
}