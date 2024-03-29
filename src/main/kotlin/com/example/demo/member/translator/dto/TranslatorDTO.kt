package com.example.demo.member.translator.dto

import com.example.demo.member.user.dto.UserDto
import com.example.demo.member.user.entity.User
import com.example.demo.member.heart.dto.TranslatorHeartResponseDTO
import com.example.demo.member.translator.entity.Translator
import com.example.demo.member.translator.type.TranslatorCategory
import com.example.demo.member.translator.type.TranslatorLevel

class TranslatorDTO(
    val id: Long?,
    val career: Int,
    val level: TranslatorLevel,
    val user: UserDto,
    val certificates: MutableList<String>,
    val categories: MutableList<TranslatorCategory>,

    ) {
    var hearts: MutableList<TranslatorHeartResponseDTO>? = null

    constructor(translator: Translator) : this(
        translator.id,
        translator.career,
        translator.level,
        UserDto(translator.user),
        translator.certificates,
        translator.categories,
    ) {
        if (translator.hearts.isNotEmpty())
            hearts = translator.hearts.map { TranslatorHeartResponseDTO(it) }.toMutableList()
    }

    //heart는 엔티티에 포함되지 않음.
    fun toEntity(user : User): Translator = Translator(id, career, user, level, certificates, categories)

    fun toResponseDTO() : TranslatorResponseDTO = TranslatorResponseDTO(id, career, user.id, level, hearts, certificates, categories)

}