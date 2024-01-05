package com.example.demo.user.translator.entity

import com.example.demo.common.database.converter.StringFlatter
import com.example.demo.user.basic.entity.User
import com.example.demo.user.translator.converter.TranslatorCategoryFlatter
import com.example.demo.user.translator.type.TranslatorCategory
import com.example.demo.user.translator.type.TranslatorLevel
import jakarta.persistence.*

@Entity
class Translator(
    userId: String,
    password: String,
    name: String,
    phone: String,
    email: String?,
    language: List<String>,
    @Column
    var career: Int,

    @Column(length = 20)
    @Enumerated(EnumType.STRING) //enum값 그대로 string으로 저장
    var level: TranslatorLevel,

    @Column
    @Convert(converter = StringFlatter::class)
    var certificates: List<String>,

    @Column
    @Convert(converter = TranslatorCategoryFlatter::class)
    var categories: List<TranslatorCategory>

) : User(userId, password, name, phone, email, language)