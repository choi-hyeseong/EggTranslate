package com.example.demo.translate.dto

import com.example.demo.file.dto.FileDTO
import com.example.demo.translate.entity.TranslateFile

class TranslateFileDTO(
    val id : Long = -1,
    val file: FileDTO,
    val autoTranslate: AutoTranslateDTO
) {

    constructor(translateFile: TranslateFile) : this(translateFile.id, FileDTO(translateFile.file), AutoTranslateDTO(translateFile.autoTranslate))
    fun toEntity(): TranslateFile = TranslateFile(id, file.toEntity(), autoTranslate.toEntity())

}