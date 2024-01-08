package com.example.demo.file.manual.entity

import com.example.demo.file.auto.entity.AutoTranslate
import com.example.demo.file.request.entity.TranslateRequest
import jakarta.persistence.*

@Entity
class ManualTranslate(

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