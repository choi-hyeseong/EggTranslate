package com.example.demo.translate.auto.dto

import com.example.demo.translate.auto.entity.AutoTranslate
import com.example.demo.translate.auto.entity.TranslateResult
import com.example.demo.user.basic.dto.UserDto
import com.example.demo.user.basic.entity.User
import com.example.demo.user.parent.child.dto.ChildDTO

class TranslateResultSaveDTO(
    val id: Long = -1,
    val user: UserDto,
    val autoTranslate: AutoTranslateDTO,
    val child: ChildDTO?
) {
    constructor(translateResult: TranslateResult) : this(
        translateResult.id,
        UserDto(translateResult.user),
        AutoTranslateDTO(translateResult.autoTranslate),
        translateResult.child?.let {
            ChildDTO(it)
        }
    )

    fun toEntity(user : User, autoTranslate: AutoTranslate): TranslateResult = TranslateResult(
        id,
        user,
        user.userType,
        autoTranslate,
        child?.toEntity()
    )
}