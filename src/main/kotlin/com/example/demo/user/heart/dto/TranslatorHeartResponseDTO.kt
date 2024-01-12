package com.example.demo.user.heart.dto

import com.example.demo.user.heart.entity.TranslatorHeart

//응답전용 DTO. 일반 DTO내에 넣으면 재귀 Exception 발생
class TranslatorHeartResponseDTO(
    val id: Long?,
    val userId: Long?,
    val translatorId : Long?
) {
    constructor(translatorHeart: TranslatorHeart) : this(translatorHeart.id, translatorHeart.user?.id,
        translatorHeart.translator?.id
    )
}
