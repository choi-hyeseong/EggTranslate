package com.example.demo.member.translator.entity

import com.example.demo.common.database.converter.StringFlatter
import com.example.demo.member.user.entity.User
import com.example.demo.member.heart.entity.TranslatorHeart
import com.example.demo.member.translator.converter.TranslatorCategoryFlatter
import com.example.demo.member.translator.dto.TranslatorUpdateDTO
import com.example.demo.member.translator.type.TranslatorCategory
import com.example.demo.member.translator.type.TranslatorLevel
import jakarta.persistence.*

@Entity
class Translator(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long?,

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

    fun update(translatorUpdateDTO: TranslatorUpdateDTO) {
        if (translatorUpdateDTO.career != null)
            this.career = translatorUpdateDTO.career

        if (translatorUpdateDTO.level != null)
            this.level = translatorUpdateDTO.level

        if (translatorUpdateDTO.certificates != null)
            this.certificates = translatorUpdateDTO.certificates

        if (translatorUpdateDTO.categories != null)
            this.categories = translatorUpdateDTO.categories
    }


}