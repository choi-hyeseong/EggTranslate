package com.example.demo.translate.auto.dto

import com.example.demo.docs.dto.ConvertDocumentDTO
import com.example.demo.docs.dto.DocumentDTO
import com.example.demo.docs.entity.ConvertDocument
import com.example.demo.docs.entity.Document
import com.example.demo.file.dto.FileDTO
import com.example.demo.file.entity.File
import com.example.demo.file.dto.ConvertFileDTO
import com.example.demo.translate.auto.entity.TranslateFile
import com.example.demo.user.basic.entity.User
import com.example.demo.voca.dto.VocaDTO
import com.example.demo.voca.dto.VocaResponseDTO

class TranslateFileDTO(
    val id: Long?,
    val file: FileDTO?,
    val convert: ConvertFileDTO?,
    val document: DocumentDTO?,
    val convertDocument: ConvertDocumentDTO?,
    val vocaDto: MutableList<VocaResponseDTO>,
    val origin: String,
    val translate: String,
    val from: String,
    val to: String
) {

    constructor(translateFile: TranslateFile) : this(
        translateFile.id,
        translateFile.file?.let { FileDTO(it) },
        translateFile.convertFile?.let { ConvertFileDTO(it) },
        translateFile.document?.let { DocumentDTO(it) },
        translateFile.convertDocument?.let { ConvertDocumentDTO(it) },
        translateFile.voca,
        translateFile.origin,
        translateFile.translate,
        translateFile.fromLang,
        translateFile.toLang
    )

    fun toEntity(file: File?, document: Document?, user: User?): TranslateFile =
        TranslateFile(id, convert?.toEntity(user), file, document, convertDocument?.toEntity(user), origin, translate, from, to, vocaDto)

    fun toResponseDTO(): TranslateFileResultDTO =
        TranslateFileResultDTO(id, convert?.id, file?.id, document?.id, convertDocument?.id, vocaDto, origin, translate, from, to)
}