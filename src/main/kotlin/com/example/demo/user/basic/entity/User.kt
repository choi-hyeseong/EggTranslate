package com.example.demo.user.basic.entity

import com.example.demo.common.database.converter.StringFlatter
import com.example.demo.common.database.entity.BaseEntity
import com.example.demo.user.basic.type.UserType
import com.example.demo.user.heart.entity.TranslatorHeart
import jakarta.persistence.*

// TODO FCM TOKEN
@Entity
class User(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = -1,

    @Column(nullable = false, unique = true, length = 30)
    var username: String,

    @Column(nullable = false, unique = false, length = 50)
    var password: String,

    @Column(nullable = false, unique = false, length = 50)
    var name: String,

    @Column(nullable = false, unique = false, length = 35)
    var phone: String,

    @Column(nullable = true, unique = false, length = 50)
    var email: String?,

    @Column
    @Convert(converter = StringFlatter::class)
    var language: MutableList<String>,

    @Enumerated(value = EnumType.STRING)
    var userType: UserType

) : BaseEntity() {

    @OneToMany(cascade = [CascadeType.ALL], mappedBy = "user")
    var heartList : MutableList<TranslatorHeart> = mutableListOf()

}
