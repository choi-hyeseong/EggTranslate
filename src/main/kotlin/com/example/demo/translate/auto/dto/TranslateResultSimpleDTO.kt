package com.example.demo.translate.auto.dto

import com.example.demo.translate.auto.entity.TranslateResult
import com.example.demo.translate.manual.dto.ManualResultResponseDTO

data class TranslateResultSimpleDTO(
    val id: Long?,
    val userId: Long?,
    val autoTranslate: Long?,
    val childId: Long?,
    val manualResult: Long?
) {
    constructor(translateResult: TranslateResult) : this(
        translateResult.id,
        translateResult.user?.id,
        translateResult.autoTranslate.id,
        translateResult.child?.id,
        translateResult.manualResult?.id
    )

    constructor(translateResultDTO: TranslateResultDTO) : this(
        translateResultDTO.id,
        translateResultDTO.user?.id,
        translateResultDTO.autoTranslate.id,
        translateResultDTO.child?.id,
        translateResultDTO.manualResultDTO?.id
    )


}