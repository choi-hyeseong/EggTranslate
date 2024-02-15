package com.example.demo.translate.auto.dto

import com.example.demo.convertOrNull
import com.example.demo.translate.auto.entity.AutoTranslate
import com.example.demo.translate.auto.entity.TranslateResult
import com.example.demo.translate.manual.dto.ManualResultDTO
import com.example.demo.translate.manual.dto.ManualResultResponseDTO
import com.example.demo.translate.manual.entity.ManualResult
import com.example.demo.member.user.dto.UserDto
import com.example.demo.member.user.entity.User
import com.example.demo.member.user.type.UserType
import com.example.demo.member.parent.child.dto.ChildDTO

class TranslateResultDTO(
    val id: Long?,
    val user: UserDto?,

    val autoTranslate: AutoTranslateDTO,
    val child: ChildDTO?,
    var manualResultDTO: ManualResultDTO?
) {

    var userType : UserType = UserType.GUEST

    constructor(translateResult: TranslateResult) : this(
        translateResult.id,
        translateResult.user.convertOrNull { UserDto(it) },
        AutoTranslateDTO(translateResult.autoTranslate),
        translateResult.child.convertOrNull { ChildDTO(it) },
        translateResult.manualResult.convertOrNull { ManualResultDTO(it) }
    ) {
        this.userType = translateResult.getUserType()
    }

    fun toResponseDTO(): TranslateResultResponseDTO =
        TranslateResultResponseDTO(
            id,
            user?.id,
            userType,
            autoTranslate.toResponseDTO(),
            child?.id,
            manualResultDTO.convertOrNull {
                ManualResultResponseDTO(it.translateList.map { translate ->
                    translate.toResponseDTO()
                }.toMutableList(), it.translatorDTO?.id, it.status)
            })

    fun toEntity(user : User?, autoTranslate: AutoTranslate, result: ManualResult?): TranslateResult = TranslateResult(
        id,
        user,
        autoTranslate,
        child?.toEntity()
    ).apply {
        manualResult = result
    }
}
