package com.example.demo.translate.auto.dto

import com.example.demo.convertOrNull
import com.example.demo.translate.auto.entity.TranslateResult
import com.example.demo.translate.manual.dto.ManualResultResponseDTO
import com.example.demo.member.user.type.UserType

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
        translateResult.user?.id,
        translateResult.getUserType(),
        AutoTranslateResponseDTO(translateResult.autoTranslate),
        translateResult.child?.id,
        translateResult.manualResult.convertOrNull {
            ManualResultResponseDTO(it)
        }
    )


}