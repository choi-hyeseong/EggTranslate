package com.example.demo.translate.auto.dto

import com.example.demo.translate.auto.entity.TranslateFile
import com.example.demo.voca.dto.VocaDTO
import com.example.demo.voca.dto.VocaResponseDTO
import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(value = JsonInclude.Include.NON_NULL)
class TranslateFileResultDTO(
    val id : Long?,
    val convertFile : Long?,
    val fileId : Long?,
    val documentId : Long?,
    val convertDocumentId : Long?,
    val voca : MutableList<VocaResponseDTO>,
    val origin : String,
    val translate : String,
    val from : String,
    val to : String
) {
    constructor(translateFile: TranslateFile) : this(
        translateFile.id,
        translateFile.convertFile?.id,
        translateFile.file?.id,
        translateFile.document?.id,
        translateFile.convertDocument?.id,
        translateFile.voca,
        translateFile.origin,
        translateFile.translate,
        translateFile.fromLang,
        translateFile.toLang)
}