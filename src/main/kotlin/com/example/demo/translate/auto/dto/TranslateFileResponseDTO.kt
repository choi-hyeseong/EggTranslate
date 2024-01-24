package com.example.demo.translate.auto.dto

import com.example.demo.file.dto.ConvertFileDTO
import com.example.demo.translate.auto.entity.TranslateFile
import com.example.demo.voca.dto.VocaDTO
import com.example.demo.voca.dto.VocaResponseDTO
import com.example.demo.voca.entity.Voca
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToMany

data class TranslateFileResponseDTO(
    var id : Long?,
    val isSuccess: Boolean,
    var fileId: Long?,
    var convert : ConvertFileDTO?,
    var voca: MutableList<VocaResponseDTO>,
    val from: String,
    val target: String,
    val origin: String?,
    val result: String?
) {
    constructor(fileDTO: TranslateFile) : this(fileDTO.id,true, fileDTO.file.id,
        fileDTO.convertFile?.let { ConvertFileDTO(it) }, fileDTO.voca, fileDTO.fromLang, fileDTO.toLang, fileDTO.origin, fileDTO.translate)
}