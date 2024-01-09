package com.example.demo.file.dto

import com.example.demo.file.entity.TranslateFile
import com.example.demo.translate.dto.AutoTranslateDTO

class TranslateFileDTO (
    val file : FileDTO,
    val autoTranslate : AutoTranslateDTO
    ) {

    fun toEntity() : TranslateFile = TranslateFile(file.toEntity(), autoTranslate.toEntity())

}