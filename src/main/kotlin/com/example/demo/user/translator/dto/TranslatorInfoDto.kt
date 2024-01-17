package com.example.demo.user.translator.dto

import com.example.demo.user.basic.dto.UserInfoDTO
import com.example.demo.user.translator.entity.Translator
import com.example.demo.user.translator.type.TranslatorCategory
import com.example.demo.user.translator.type.TranslatorLevel

class TranslatorInfoDto(
    val id: Long?,
    val career: Int,
    val user : UserInfoDTO,
    val level: TranslatorLevel,
    val hearts: Int,
    val certificates: MutableList<String>,
    val categories: MutableList<TranslatorCategory>,

    ) {
    constructor(translator: Translator) : this(
        translator.id,
        translator.career,
        UserInfoDTO(translator.member),
        translator.level,
        translator.hearts.size,
        translator.certificates,
        translator.categories,
    )
}