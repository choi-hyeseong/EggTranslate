package com.example.demo.user.heart.dto

import com.example.demo.user.basic.dto.UserDto
import com.example.demo.user.heart.entity.TranslatorHeart
import com.example.demo.user.translator.dto.TranslatorDTO

class TranslatorHeartDTO(
    val user : UserDto,
    val translator: TranslatorDTO
) {

    fun toEntity() : TranslatorHeart = TranslatorHeart(
        user = user.toEntity(),
        translator = translator.toEntity()
    )
}