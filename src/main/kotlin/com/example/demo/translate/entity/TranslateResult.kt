package com.example.demo.translate.entity

import jakarta.persistence.*

@Entity
class TranslateResult(

        @Column(nullable = false)
        var translateContent : String,

        @OneToOne
        @JoinColumn(name = "translaterequest_id")
        var translateRequest : TranslateRequest
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id : Long = -1
}