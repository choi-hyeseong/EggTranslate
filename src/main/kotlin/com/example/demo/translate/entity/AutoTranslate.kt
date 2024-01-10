package com.example.demo.translate.entity

import com.example.demo.user.basic.entity.User
import jakarta.persistence.*

@Entity
@Table(name = "autotranslate")
class AutoTranslate(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = -1,

    @ManyToOne
    @JoinColumn(name = "user_id")
    var user: User,

    @OneToMany(fetch = FetchType.EAGER) //mappedBy -> table명이 아닌 field(property) 명
    @JoinColumn(name = "autotranslate_id")
    var translateFiles: MutableList<TranslateFile> = mutableListOf()

) {





}