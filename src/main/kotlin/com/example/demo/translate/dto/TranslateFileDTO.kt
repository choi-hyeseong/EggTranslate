package com.example.demo.translate.dto

import com.example.demo.file.dto.FileDTO
import com.example.demo.translate.entity.TranslateFile

class TranslateFileDTO(
    val id : Long = -1,
    val file: FileDTO,
    val origin : String,
    val translate : String,
    val from : String,
    val to : String
) {

    constructor(translateFile: TranslateFile) : this(translateFile.id, FileDTO(translateFile.file), translateFile.origin, translateFile.translate, translateFile.fromLang, translateFile.toLang)
    fun toEntity(): TranslateFile = TranslateFile(id, file.toEntity(), origin, translate, from, to)

}