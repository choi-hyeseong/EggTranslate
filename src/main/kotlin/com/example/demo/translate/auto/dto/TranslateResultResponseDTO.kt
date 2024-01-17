package com.example.demo.translate.auto.dto

import com.example.demo.translate.auto.entity.TranslateResult
import com.example.demo.translate.manual.dto.ManualResultResponseDTO

data class TranslateResultResponseDTO(
    val id: Long?,
    val userId: Long?,
    val autoTranslate: AutoTranslateResponseDTO,
    val childId: Long?,
    val manualResult: ManualResultResponseDTO?
) {
    constructor(translateResult: TranslateResult) : this(
        translateResult.id,
        translateResult.member.id,
        AutoTranslateResponseDTO(translateResult.autoTranslate),
        translateResult.child?.id,
        translateResult.manualResult?.let {
            ManualResultResponseDTO(it)
        }
    )


}