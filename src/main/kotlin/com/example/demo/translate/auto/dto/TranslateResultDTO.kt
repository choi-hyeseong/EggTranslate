package com.example.demo.translate.auto.dto

import com.example.demo.translate.auto.entity.TranslateResult
import com.example.demo.translate.manual.dto.ManualResultDTO
import com.example.demo.translate.manual.dto.ManualResultResponseDTO
import com.example.demo.user.basic.dto.UserDto
import com.example.demo.user.parent.child.dto.ChildDTO

class TranslateResultDTO(
    val id: Long = -1,
    val user: UserDto,
    val autoTranslate: AutoTranslateDTO,
    val child: ChildDTO?,
    var manualResultDTO: ManualResultDTO?
) {
    constructor(translateResult: TranslateResult) : this(
        translateResult.id,
        UserDto(translateResult.user),
        AutoTranslateDTO(translateResult.autoTranslate),
        translateResult.child?.let {
            ChildDTO(it)
        },
        translateResult.manualResult?.let {
            ManualResultDTO(it)
        }
    )

    fun toResponseDTO(): TranslateResultResponseDTO =
        TranslateResultResponseDTO(
            id,
            user.id,
            AutoTranslateResponseDTO(autoTranslate.toEntity()),
            child?.id,
            manualResultDTO?.let {
                ManualResultResponseDTO(it.translateList.map { translate ->
                    translate.toResponseDTO()
                }.toMutableList(), it.translatorDTO.id, it.status)
            })

    fun toEntity(): TranslateResult = TranslateResult(
        id,
        user.toEntity(),
        user.userType,
        autoTranslate.toEntity(),
        child?.toEntity()
    ).apply {
        manualResultDTO?.let {
            manualResult = it.toEntity()
        }
    }
}
