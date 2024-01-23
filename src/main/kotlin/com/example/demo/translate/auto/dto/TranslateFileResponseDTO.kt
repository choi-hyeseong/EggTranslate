package com.example.demo.translate.auto.dto

import com.example.demo.file.dto.ConvertFileDTO
import com.example.demo.translate.auto.entity.TranslateFile

data class TranslateFileResponseDTO(
    var id : Long?,
    val isSuccess: Boolean,
    var fileId: Long?,
    var convert : ConvertFileDTO?,
    val from: String,
    val target: String,
    val origin: String?,
    val result: String?
) {
    constructor(fileDTO: TranslateFile) : this(fileDTO.id,true, fileDTO.file.id,
        fileDTO.convertFile?.let { ConvertFileDTO(it) }, fileDTO.fromLang, fileDTO.toLang, fileDTO.origin, fileDTO.translate)
}