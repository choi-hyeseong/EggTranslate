package com.example.demo.translate.manual.dto

import com.example.demo.translate.manual.entity.ManualTranslate

data class ManualTranslateResponseDTO(
    val id : Long = -1,
    val translateFileId : Long,
    val content : String,

    ) {
    constructor(manualTranslate: ManualTranslate) : this(manualTranslate.id, manualTranslate.translateFile.id, manualTranslate.translateContent)

}