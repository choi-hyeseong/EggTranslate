package com.example.demo.translate.manual.dto

import com.example.demo.translate.auto.dto.TranslateFileDTO
import com.example.demo.translate.auto.entity.TranslateFile
import com.example.demo.translate.manual.entity.ManualTranslate

data class ManualTranslateDTO(
    val id : Long?,
    val translate : TranslateFileDTO,
    val content : String,

    ) {
    constructor(manualTranslate: ManualTranslate) : this(manualTranslate.id, TranslateFileDTO(manualTranslate.translateFile), manualTranslate.translateContent)


    fun toResponseDTO() : ManualTranslateResponseDTO = ManualTranslateResponseDTO(translate.id, content)
}