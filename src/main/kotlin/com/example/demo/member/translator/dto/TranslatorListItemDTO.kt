package com.example.demo.member.translator.dto

import com.example.demo.member.translator.entity.Translator
import com.example.demo.member.translator.type.TranslatorCategory
import org.springframework.util.Assert

class TranslatorListItemDTO (
    val career: Int,
    val categories: MutableList<TranslatorCategory>
) {
    lateinit var name : String
    var id: Long = -1
    var user: Long = -1

    constructor(translator : Translator) : this(translator.career, translator.categories) {
        Assert.notNull(translator.id, "번역가의 id는 null이 와선 안됩니다.")
        Assert.notNull(translator.user.id, "번역가의 유저 id는 null이 와선 안됩니다.")
        Assert.notNull(translator.user.name, "번역가의 유저 이름은 null이 와선 안됩니다.")
        this.id = translator.id!!
        this.user = translator.user.id!!
        this.name = translator.user.name
    }
}
