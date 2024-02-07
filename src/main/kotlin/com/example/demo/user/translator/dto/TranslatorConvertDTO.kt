package com.example.demo.user.translator.dto

import com.example.demo.user.basic.dto.UserDto
import com.example.demo.user.translator.type.TranslatorCategory
import com.example.demo.user.translator.type.TranslatorLevel

class TranslatorConvertDTO (
    val career: Int,
    val level: TranslatorLevel,
    val certificates: MutableList<String>,
    val categories: MutableList<TranslatorCategory>
) {
    fun toTranslatorDTO(userDto: UserDto) : TranslatorDTO = TranslatorDTO(null, career, level, userDto, certificates, categories)
}