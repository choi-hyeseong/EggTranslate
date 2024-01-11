package com.example.demo.translate.manual.dto

import com.example.demo.translate.manual.entity.ManualTranslate

data class ManualTranslateResponseDTO(
    val translateFileId : Long,
    val content : String,

    ) {
    constructor(manualTranslate: ManualTranslate) : this(manualTranslate.translateFile.id, manualTranslate.translateContent)

}