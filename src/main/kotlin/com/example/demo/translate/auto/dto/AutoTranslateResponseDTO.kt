package com.example.demo.translate.auto.dto

import com.example.demo.translate.auto.entity.AutoTranslate

class AutoTranslateResponseDTO(
    val id : Long,
    val translateFiles : List<TranslateFileResponseDTO>
) {
    constructor(autoTranslate: AutoTranslate) : this(autoTranslate.id, autoTranslate.translateFiles.map {
        TranslateFileResponseDTO(it)
    }.toMutableList())
}