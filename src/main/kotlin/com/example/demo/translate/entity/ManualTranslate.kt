package com.example.demo.translate.entity

import jakarta.persistence.*

@Entity
class ManualTranslate(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = -1,

    @Column(nullable = false)
    var translateContent: String,

    @OneToOne
    @JoinColumn(name = "translaterequest_id")
    var manualRequest: ManualRequest
) {

}