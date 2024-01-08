package com.example.demo.user.translator.dto

import com.example.demo.user.basic.dto.UserDto
import com.example.demo.user.translator.entity.Translator
import com.example.demo.user.translator.type.TranslatorCategory
import com.example.demo.user.translator.type.TranslatorLevel

class TranslatorDTO(
    val career : Int,
    val level : TranslatorLevel,
    val user : UserDto,
    val certificates : List<String>,
    val categories : List<TranslatorCategory>,

    ) {

    constructor(translator: Translator) : this(translator.career, translator.level, UserDto(translator.user), translator.certificates, translator.categories)
    fun toEntity() : Translator = Translator( career, user.toEntity(userDTO.id, userDTO.name, userDTO.password, userDTO.phone, userDTO.email, userDTO.languages), level, certificates, categories)
}