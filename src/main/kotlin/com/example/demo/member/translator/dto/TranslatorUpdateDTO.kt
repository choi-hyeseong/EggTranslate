package com.example.demo.member.translator.dto

import com.example.demo.member.user.dto.UserUpdateDTO
import com.example.demo.member.translator.type.TranslatorCategory
import com.example.demo.member.translator.type.TranslatorLevel

class TranslatorUpdateDTO (
    val career: Int?,
    val level: TranslatorLevel?,
    val certificates: MutableList<String>?,
    val categories: MutableList<TranslatorCategory>?,
    val user : UserUpdateDTO?,
)