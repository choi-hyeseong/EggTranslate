package com.example.demo.member.translator.dto

import com.example.demo.member.user.dto.UserDto
import com.example.demo.member.translator.type.TranslatorCategory
import com.example.demo.member.translator.type.TranslatorLevel

class TranslatorConvertDTO (
    val career: Int,
    val level: TranslatorLevel,
    val certificates: MutableList<String>,
    val categories: MutableList<TranslatorCategory>
) {
    fun toTranslatorDTO(userDto: UserDto) : TranslatorDTO = TranslatorDTO(null, career, level, userDto, certificates, categories)
}