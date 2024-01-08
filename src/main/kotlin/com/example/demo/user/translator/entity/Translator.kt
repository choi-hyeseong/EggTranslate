package com.example.demo.user.translator.entity

import com.example.demo.common.database.converter.StringFlatter
import com.example.demo.user.basic.entity.User
import com.example.demo.user.translator.converter.TranslatorCategoryFlatter
import com.example.demo.user.translator.type.TranslatorCategory
import com.example.demo.user.translator.type.TranslatorLevel
import jakarta.persistence.*

@Entity
class Translator(
    @Column
    var career: Int,

    @OneToOne
    @JoinColumn(name = "user_id")
    var user: User,

    @Column(length = 20)
    @Enumerated(EnumType.STRING) //enum값 그대로 string으로 저장
    var level: TranslatorLevel,

    @Column
    @Convert(converter = StringFlatter::class)
    var certificates: List<String>,

    @Column
    @Convert(converter = TranslatorCategoryFlatter::class)
    var categories: List<TranslatorCategory>

) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = -1



}