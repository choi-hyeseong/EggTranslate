package com.example.demo.translate.auto.dto

import com.example.demo.convertOrNull
import com.example.demo.docs.dto.ConvertDocumentDTO
import com.example.demo.file.dto.ConvertFileDTO
import com.example.demo.translate.auto.entity.TranslateFile
import com.example.demo.voca.dto.VocaResponseDTO

//번역이 끝난 후 데이터 (클라이언트 리턴용 Response 아님)
data class TranslateFileResponseDTO(
    var id: Long?,
    val isSuccess: Boolean,
    val translateFileData: TranslateFileData,
    val translateResultData: TranslateResultData
) {
    constructor(fileDTO: TranslateFile) : this(
        fileDTO.id,
        true,
        TranslateFileData(
            fileDTO.id,
            fileDTO.convertFile.convertOrNull { ConvertFileDTO(it) },
            fileDTO.document?.id,
            fileDTO.convertDocument.convertOrNull { ConvertDocumentDTO(it) }
        ),
        TranslateResultData(
            fileDTO.voca,
            fileDTO.fromLang,
            fileDTO.toLang,
            fileDTO.origin,
            fileDTO.translate
        )
    )


}

class TranslateFileData(
    var fileId: Long?,
    var convert: ConvertFileDTO?,
    var documentId: Long?,
    var convertDocumentDTO: ConvertDocumentDTO?
)

class TranslateResultData(
    var voca: MutableList<VocaResponseDTO>,
    val from: String,
    val to: String,
    val origin: String?,
    val result: String?
)