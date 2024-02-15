package com.example.demo.translate.auto.entity

import jakarta.persistence.*

@Entity
@Table(name = "autotranslate")
class AutoTranslate(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long?,

    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.EAGER) //mappedBy -> table명이 아닌 field(property) 명
    @JoinColumn(name = "autotranslate_id")
    var translateFiles: MutableList<TranslateFile> = mutableListOf()

) {





}