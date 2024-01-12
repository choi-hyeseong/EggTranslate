package com.example.demo.translate.auto.dto

import com.example.demo.translate.auto.entity.TranslateFile

class TranslateFileResultDTO(
    val id : Long?,
    val fileId : Long?,
    val origin : String,
    val translate : String,
    val from : String,
    val to : String
) {
    constructor(translateFile: TranslateFile) : this(
        translateFile.id,
        translateFile.file.id,
        translateFile.origin,
        translateFile.translate,
        translateFile.fromLang,
        translateFile.toLang)
}