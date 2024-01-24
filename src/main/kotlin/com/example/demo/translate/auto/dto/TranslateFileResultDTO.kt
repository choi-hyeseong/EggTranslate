package com.example.demo.translate.auto.dto

import com.example.demo.translate.auto.entity.TranslateFile
import com.example.demo.voca.dto.VocaDTO
import com.example.demo.voca.dto.VocaResponseDTO

class TranslateFileResultDTO(
    val id : Long?,
    val convertFile : Long?,
    val fileId : Long?,
    val voca : MutableList<VocaResponseDTO>,
    val origin : String,
    val translate : String,
    val from : String,
    val to : String
) {
    constructor(translateFile: TranslateFile) : this(
        translateFile.id,
        translateFile.convertFile?.id,
        translateFile.file.id,
        translateFile.voca,
        translateFile.origin,
        translateFile.translate,
        translateFile.fromLang,
        translateFile.toLang)
}