package com.example.demo.translate.auto.dto

import com.example.demo.docs.dto.ConvertDocumentDTO
import com.example.demo.file.dto.ConvertFileDTO
import com.example.demo.translate.auto.entity.TranslateFile
import com.example.demo.voca.dto.VocaDTO
import com.example.demo.voca.dto.VocaResponseDTO
import com.example.demo.voca.entity.Voca
import com.fasterxml.jackson.annotation.JsonInclude
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToMany

//번역이 끝난 후 데이터 (클라이언트 리턴용 Response 아님)
data class TranslateFileResponseDTO(
    var id: Long?,
    val isSuccess: Boolean,
    var fileId: Long?,
    var convert: ConvertFileDTO?,
    var documentId : Long?,
    var convertDocumentDTO: ConvertDocumentDTO?,
    var voca: MutableList<VocaResponseDTO>,
    val from: String,
    val target: String,
    val origin: String?,
    val result: String?
) {
    constructor(fileDTO: TranslateFile) : this(
        fileDTO.id,
        true,
        fileDTO.file?.id,
        fileDTO.convertFile?.let { ConvertFileDTO(it) },
        fileDTO.document?.id,
        fileDTO.convertDocument?.let { ConvertDocumentDTO(it) },
        fileDTO.voca,
        fileDTO.fromLang,
        fileDTO.toLang,
        fileDTO.origin,
        fileDTO.translate
    )
}