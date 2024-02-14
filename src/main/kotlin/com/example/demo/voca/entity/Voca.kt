package com.example.demo.voca.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
class Voca(
    /*
    *   추후 해당 단어에 대한 뜻이 필요하다면 origin에 해당하는 meaning / translate에 대한 meaning 2개의 필드가 필요하다.
    *   그렇게 될 경우 KoreanVoca와 TranslateVoca 테이블 2개로 나뉠 필요가 있어보임
    *   KoreanVoca는 한국어 단어와 그에 해당하는 뜻, TranslateVoca는 KoreanVoca에 대한 FK, 번역된 단어와 번역된 뜻으로 구성 ( 1 : N )
    */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id : Long?,

    @Column(length = 4)
    val lang : String,

    @Column(columnDefinition = "TEXT")
    var origin : String,

    @Column(columnDefinition = "TEXT")
    var translate : String,
)