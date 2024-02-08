package com.example.demo.user.basic.entity

import com.example.demo.common.database.converter.StringFlatter
import com.example.demo.common.database.entity.BaseEntity
import com.example.demo.user.basic.dto.UserUpdateDTO
import com.example.demo.user.basic.type.UserType
import com.example.demo.user.heart.entity.TranslatorHeart
import jakarta.persistence.*

// TODO FCM TOKEN
@Entity
class User(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long?,

    @Column(nullable = false, unique = true, length = 30)
    var username: String,

    @Column(nullable = false, unique = false, columnDefinition = "TEXT")
    var password: String,

    @Column(nullable = false, unique = false, length = 50)
    var name: String,

    @Column(nullable = false, unique = false, length = 35)
    var phone: String,

    @Column(nullable = false, unique = true, length = 50)
    var email: String,

    @Column
    @Convert(converter = StringFlatter::class)
    var language: MutableList<String>,

    @Enumerated(value = EnumType.STRING)
    var userType: UserType

) : BaseEntity() {

    @OneToMany(cascade = [CascadeType.ALL], mappedBy = "user")
    var heartList: MutableList<TranslatorHeart> = mutableListOf()

    fun addHeart(translatorHeart: TranslatorHeart) {
        if (heartList.all { it.id != translatorHeart.id })
            heartList.add(translatorHeart)
    }

    fun removeHeart(translatorHeart: TranslatorHeart) {
        heartList.removeIf { it.id == translatorHeart.id }
    }

    fun update(update: UserUpdateDTO) {
        if (update.name != null)
            this.name = update.name

        if (update.password != null)
            this.password = update.password

        if (update.phone != null)
            this.phone = update.phone

        if (update.email != null)
            this.email = update.email

        if (!update.languages.isNullOrEmpty())
            this.language = update.languages
    }
}
