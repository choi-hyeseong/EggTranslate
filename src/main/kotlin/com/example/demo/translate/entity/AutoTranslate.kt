package com.example.demo.translate.entity

import com.example.demo.user.basic.entity.User
import jakarta.persistence.*

@Entity
@Table(name = "autotranslate")
class AutoTranslate(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = -1,

    @OneToOne
    @JoinColumn(name = "user_id")
    var user: User,

    @Column(nullable = false)
    var origin: String,

    @Column(nullable = false)
    var translate: String,

    @Column(nullable = false, length = 10)
    var fromLang: String,

    @Column(nullable = false, length = 10)
    var toLang: String,

    @OneToMany(cascade = [CascadeType.ALL], mappedBy = "autoTranslate") //mappedBy -> table명이 아닌 field(property) 명
    var translateFiles: MutableList<TranslateFile> = mutableListOf()

) {





}