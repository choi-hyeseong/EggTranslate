package com.example.demo.translate.entity

import jakarta.persistence.*

@Entity
class TranslationResult(

        @Column(nullable = false)
        var translateContent : String,

        @OneToOne
        @JoinColumn(name = "translaterequest_id")
        var translationRequest : TranslationRequest
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id : Long = -1
}