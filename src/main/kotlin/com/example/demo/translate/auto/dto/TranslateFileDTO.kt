package com.example.demo.translate.auto.dto

import com.example.demo.file.dto.FileDTO
import com.example.demo.file.entity.File
import com.example.demo.file.dto.ConvertFileDTO
import com.example.demo.translate.auto.entity.TranslateFile
import com.example.demo.user.basic.entity.User
import com.example.demo.voca.dto.VocaDTO
import com.example.demo.voca.dto.VocaResponseDTO

class TranslateFileDTO(
    val id : Long?,
    val file: FileDTO,
    val convert: ConvertFileDTO?,
    val vocaDto: MutableList<VocaResponseDTO>,
    val origin : String,
    val translate : String,
    val from : String,
    val to : String
) {

    constructor(translateFile: TranslateFile) : this(translateFile.id, FileDTO(translateFile.file),
        translateFile.convertFile?.let { ConvertFileDTO(it) }, translateFile.voca, translateFile.origin, translateFile.translate, translateFile.fromLang, translateFile.toLang)
    fun toEntity(file : File, user : User?): TranslateFile = TranslateFile(id, convert?.toEntity(user), file,  origin, translate, from, to, vocaDto)

    fun toResponseDTO() : TranslateFileResultDTO = TranslateFileResultDTO(id, convert?.id, file.id, vocaDto, origin, translate, from, to)
}