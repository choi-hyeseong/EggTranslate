package com.example.demo.user.translator.dto

import com.example.demo.user.translator.entity.Translator
import com.example.demo.user.translator.type.TranslatorCategory
import com.example.demo.user.translator.type.TranslatorLevel

class TranslatorDTO(
    val userId : String,
    val password : String,
    val name : String,
    val phone : String,
    val email : String?,
    val language : List<String>,
    val career : Int,
    val level : TranslatorLevel,
    val certificates : List<String>,
    val categories : List<TranslatorCategory>
) {
    fun toEntity() : Translator = Translator(userId, password, name, phone, email, language, career, level, certificates, categories)
}