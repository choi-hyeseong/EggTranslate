package com.example.demo.user.translator.dto

import com.example.demo.user.basic.dto.UserDto
import com.example.demo.user.basic.entity.User
import com.example.demo.user.translator.entity.Translator
import com.example.demo.user.translator.type.TranslatorCategory
import com.example.demo.user.translator.type.TranslatorLevel

class TranslatorDTO(
    val id : Long = -1,
    val career : Int,
    val level : TranslatorLevel,
    val user : UserDto,
    val certificates : MutableList<String>,
    val categories : MutableList<TranslatorCategory>,

    ) {

    constructor(translator: Translator) : this(translator.id, translator.career, translator.level, UserDto(translator.user), translator.certificates, translator.categories)
    fun toEntity() : Translator = Translator(id, career, user.toEntity(), level, certificates, categories)
}