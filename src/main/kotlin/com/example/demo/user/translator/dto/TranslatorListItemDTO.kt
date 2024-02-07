package com.example.demo.user.translator.dto

import com.example.demo.user.basic.dto.UserDto
import com.example.demo.user.heart.dto.TranslatorHeartResponseDTO
import com.example.demo.user.translator.entity.Translator
import com.example.demo.user.translator.type.TranslatorCategory
import com.example.demo.user.translator.type.TranslatorLevel
import org.springframework.util.Assert

class TranslatorListItemDTO (
    val career: Int,
    val categories: MutableList<TranslatorCategory>
) {
    var id: Long = -1
    var user: Long = -1

    constructor(translator : Translator) : this(translator.career, translator.categories) {
        Assert.notNull(translator.id, "번역가의 id는 null이 와선 안됩니다.")
        Assert.notNull(translator.user.id, "번역가의 유저 id는 null이 와선 안됩니다.")
        this.id = translator.id!!
        this.user = translator.user.id!!
    }
}
