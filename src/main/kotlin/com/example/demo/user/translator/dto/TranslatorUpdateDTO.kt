package com.example.demo.user.translator.dto

import com.example.demo.user.basic.dto.UserUpdateDTO
import com.example.demo.user.translator.type.TranslatorCategory
import com.example.demo.user.translator.type.TranslatorLevel

class TranslatorUpdateDTO (
    val career: Int?,
    val level: TranslatorLevel?,
    val certificates: MutableList<String>?,
    val categories: MutableList<TranslatorCategory>?,
    val user : UserUpdateDTO?,
)