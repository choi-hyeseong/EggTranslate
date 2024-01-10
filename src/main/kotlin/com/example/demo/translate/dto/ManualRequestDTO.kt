package com.example.demo.translate.dto

import com.example.demo.translate.entity.ManualRequest
import com.example.demo.translate.type.TranslateState
import com.example.demo.user.basic.dto.UserDto
import com.example.demo.user.parent.child.dto.ChildRequestDto
import com.example.demo.user.translator.dto.TranslatorDTO

class ManualRequestDTO(
    val id: Long = -1,
    val user: UserDto,
    val status: TranslateState,
    val translator: TranslatorDTO,
    val autoTranslate: AutoTranslateDTO,
    val child: ChildRequestDto?
) {
    constructor(manualRequest: ManualRequest) : this(
        manualRequest.id,
        UserDto(manualRequest.user),
        manualRequest.status,
        TranslatorDTO(manualRequest.translator),
        AutoTranslateDTO(manualRequest.autoTranslate),
        manualRequest.child?.let {
            ChildRequestDto(it)
        }
    )

    fun toEntity(): ManualRequest = ManualRequest(
        id,
        user.toEntity(),
        status,
        user.userType,
        translator.toEntity(),
        autoTranslate.toEntity(),
        child?.toEntity()
    )
}