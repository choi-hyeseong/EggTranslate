package com.example.demo.translate.dto

import com.example.demo.translate.entity.ManualResult
import com.example.demo.translate.entity.ManualTranslate
import com.example.demo.user.translator.dto.TranslatorDTO

data class ManualTranslateResponseDTO(
    val id : Long = -1,
    val translateFileId : Long,
    val content : String,

    ) {
    constructor(manualTranslate: ManualTranslate) : this(manualTranslate.id, manualTranslate.translateFile.id, manualTranslate.translateContent)

}