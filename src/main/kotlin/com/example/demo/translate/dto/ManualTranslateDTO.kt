package com.example.demo.translate.dto

import com.example.demo.translate.entity.ManualResult
import com.example.demo.translate.entity.ManualTranslate
import com.example.demo.user.translator.dto.TranslatorDTO

data class ManualTranslateDTO(
    val id : Long = -1,
    val translate : TranslateFileDTO,
    val content : String,

) {
    constructor(manualTranslate: ManualTranslate) : this(manualTranslate.id, TranslateFileDTO(manualTranslate.translateFile), manualTranslate.translateContent)

    fun toEntity() : ManualTranslate = ManualTranslate(id, translate.toEntity(), content)
}