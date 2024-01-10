package com.example.demo.translate.dto

import com.example.demo.translate.entity.AutoTranslate

class AutoTranslateResponseDTO(
    val id : Long,
    val translateFiles : List<TranslateFileResponseDTO>
) {
    constructor(autoTranslate: AutoTranslate) : this(autoTranslate.id, autoTranslate.translateFiles.map {
        TranslateFileResponseDTO(it)
    }.toMutableList())
}