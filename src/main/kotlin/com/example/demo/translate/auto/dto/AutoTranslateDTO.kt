package com.example.demo.translate.auto.dto

import com.example.demo.translate.auto.entity.AutoTranslate
import com.example.demo.translate.auto.entity.TranslateFile

class AutoTranslateDTO(
        val id : Long?,
        val translateFile: MutableList<TranslateFileDTO>
) {
        constructor(autoTranslate: AutoTranslate) : this(autoTranslate.id, autoTranslate.translateFiles.map { TranslateFileDTO(it) }.toMutableList())
        fun toEntity(translateFiles : MutableList<TranslateFile>) : AutoTranslate = AutoTranslate(id, translateFiles)

        fun toResponseDTO() = AutoTranslateResponseDTO(translateFile.map { it.toResponseDTO() })
}