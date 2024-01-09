package com.example.demo.translate.dto

import com.example.demo.translate.entity.TranslationRequest
import com.example.demo.translate.type.TranslateState
import com.example.demo.user.basic.dto.UserDto
import com.example.demo.user.basic.type.UserType
import com.example.demo.user.parent.child.dto.ChildRequestDto
import com.example.demo.user.translator.dto.TranslatorDTO

class TranslationRequestDTO(
    val id: Long = -1,
    val user: UserDto,
    val status: TranslateState,
    val translator: TranslatorDTO,
    val autoTranslate: AutoTranslateDTO,
    val child: ChildRequestDto?
) {
    constructor(translationRequest: TranslationRequest) : this(
        translationRequest.id,
        UserDto(translationRequest.user),
        translationRequest.status,
        TranslatorDTO(translationRequest.translator),
        AutoTranslateDTO(translationRequest.autoTranslate),
        translationRequest.child?.let {
            ChildRequestDto(it)
        }
    )

    fun toEntity(): TranslationRequest = TranslationRequest(
        id,
        user.toEntity(),
        status,
        user.userType,
        translator.toEntity(),
        autoTranslate.toEntity(),
        child?.toEntity()
    )
}