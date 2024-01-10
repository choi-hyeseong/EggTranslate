package com.example.demo.translate.dto

import com.example.demo.translate.entity.TranslateResult

data class TranslateResultResponseDTO(
    val id: Long,
    val userId: Long,
    val autoTranslate: AutoTranslateDTO,
    val childId: Long?,
    val manualResult: ManualResultResponseDTO?
) {
    constructor(translateResult: TranslateResult) : this(
        translateResult.id,
        translateResult.user.id,
        AutoTranslateDTO(translateResult.autoTranslate),
        translateResult.child?.id,
        translateResult.manualResult?.let {
            ManualResultResponseDTO(it)
        }
    )
}