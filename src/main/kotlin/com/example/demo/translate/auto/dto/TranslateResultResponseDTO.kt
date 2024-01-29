package com.example.demo.translate.auto.dto

import com.example.demo.translate.auto.entity.TranslateResult
import com.example.demo.translate.manual.dto.ManualResultResponseDTO
import com.example.demo.user.basic.type.UserType
import com.fasterxml.jackson.annotation.JsonInclude

data class TranslateResultResponseDTO(
    val id: Long?,
    val userId: Long?,
    val userType : UserType?,
    val autoTranslate: AutoTranslateResponseDTO,
    val childId: Long?,
    val manualResult: ManualResultResponseDTO?
) {
    constructor(translateResult: TranslateResult) : this(
        translateResult.id,
        translateResult.user.id,
        translateResult.userType,
        AutoTranslateResponseDTO(translateResult.autoTranslate),
        translateResult.child?.id,
        translateResult.manualResult?.let {
            ManualResultResponseDTO(it)
        }
    )


}