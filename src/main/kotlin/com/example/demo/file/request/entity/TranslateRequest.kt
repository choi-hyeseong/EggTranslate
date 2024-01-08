package com.example.demo.file.request.entity

import com.example.demo.file.auto.entity.AutoTranslate
import com.example.demo.user.parent.child.entity.Child
import com.example.demo.user.translator.entity.Translator
import jakarta.persistence.*

@Entity
class TranslateRequest(
        @Column(nullable = false, length = 10)
        var status : String,

        @Column(nullable = false, length = 25)
        var userType : String,

        @JoinColumn(name = "translator_id")
        var translator : Translator,

        @OneToOne
        @JoinColumn(name = "autoTranslate_id")
        var autoTranslate: AutoTranslate,

        @JoinColumn(name = "child_id")
        var child : Child,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id : Long = -1
}