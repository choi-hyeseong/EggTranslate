package com.example.demo.profile.dto

import com.example.demo.signup.dto.UserSignUpDTO
import com.example.demo.user.translator.type.TranslatorCategory
import com.example.demo.user.translator.type.TranslatorLevel

class TranslatorEditDTO(
        val career : Int,
        val level : TranslatorLevel,
        val user : UserSignUpDTO,
        val certificates : MutableList<String>,
        val categories : MutableList<TranslatorCategory>
) {
}