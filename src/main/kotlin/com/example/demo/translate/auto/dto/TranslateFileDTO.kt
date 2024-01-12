package com.example.demo.translate.auto.dto

import com.example.demo.file.dto.FileDTO
import com.example.demo.file.entity.File
import com.example.demo.translate.auto.entity.TranslateFile

class TranslateFileDTO(
    val id : Long?,
    val file: FileDTO,
    val origin : String,
    val translate : String,
    val from : String,
    val to : String
) {

    constructor(translateFile: TranslateFile) : this(translateFile.id, FileDTO(translateFile.file), translateFile.origin, translateFile.translate, translateFile.fromLang, translateFile.toLang)
    fun toEntity(file : File): TranslateFile = TranslateFile(id, file, origin, translate, from, to)

    fun toResponseDTO() : TranslateFileResultDTO = TranslateFileResultDTO(id, file.id, origin, translate, from, to)
}