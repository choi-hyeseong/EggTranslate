package com.example.demo.user.translator.dto

import com.example.demo.user.basic.dto.UserDto
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

<<<<<<< HEAD
    constructor(translator: Translator) : this(translator.career, translator.level, UserDto(translator.user), translator.certificates, translator.categories)
    fun toEntity() : Translator = Translator( career, user.toEntity(userDTO.id, userDTO.name, userDTO.password, userDTO.phone, userDTO.email, userDTO.languages), level, certificates, categories)
=======
    constructor(translator: Translator) : this(translator.id, translator.career, translator.level, UserDto(translator.user), translator.certificates, translator.categories)
    fun toEntity() : Translator = Translator(id, career, user.toEntity(), level, certificates, categories)
>>>>>>> eaaabeb3e7d82fedaef82bc170b25d9c1df9191b
}