package com.example.demo.translate.dto

import com.example.demo.translate.entity.TranslateFile

data class TranslateFileResponseDTO(
    var id : Long,
    val isSuccess: Boolean,
    var fileId: Long,
    val from: String,
    val target: String,
    val origin: String?,
    val result: String?
) {
    constructor(fileDTO: TranslateFile) : this(fileDTO.id,true, fileDTO.id, fileDTO.fromLang, fileDTO.toLang, fileDTO.origin, fileDTO.translate)
}