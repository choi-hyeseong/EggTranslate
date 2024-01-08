package com.example.demo.file.manual.entity

import com.example.demo.file.auto.entity.AutoTranslate
import com.example.demo.file.request.entity.TranslateRequest
import jakarta.persistence.*

class ManualTranslate(
        @Column(nullable = false)
        var translate_content : String,

        @OneToOne
        @JoinColumn(name = "autoTranslate_id")
        var autoTranslate : AutoTranslate,

        @OneToOne
        @JoinColumn(name = "translateRequest_id")
        var translateRequest : TranslateRequest
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id : Long = -1
}