package com.example.demo.user.translator.entity

import com.example.demo.common.database.converter.StringFlatter
import com.example.demo.user.basic.entity.User
import com.example.demo.user.heart.entity.TranslatorHeart
import com.example.demo.user.translator.converter.TranslatorCategoryFlatter
import com.example.demo.user.translator.type.TranslatorCategory
import com.example.demo.user.translator.type.TranslatorLevel
import jakarta.persistence.*

@Entity
class Translator(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = -1,

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
    var certificates: MutableList<String>,

    @Column
    @Convert(converter = TranslatorCategoryFlatter::class)
    var categories: MutableList<TranslatorCategory>

) {


    @OneToMany(mappedBy = "translator")
    var hearts: MutableList<TranslatorHeart> = mutableListOf()


    fun addHeart(translatorHeart: TranslatorHeart) {
        if (hearts.all { it.id != translatorHeart.id })
            hearts.add(translatorHeart)
    }

    fun removeHeart(heart: TranslatorHeart) {
        hearts.removeIf { it.id == heart.id }
    }


}