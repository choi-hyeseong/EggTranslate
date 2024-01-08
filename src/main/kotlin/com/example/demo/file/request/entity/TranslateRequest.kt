package com.example.demo.file.request.entity

import com.example.demo.file.auto.entity.AutoTranslate
import com.example.demo.user.basic.type.UserType
import com.example.demo.user.parent.child.entity.Child
import com.example.demo.user.translator.entity.Translator
import jakarta.persistence.*

@Entity
class TranslateRequest(

        @Column(nullable = false, length = 10)
        var status : String,

        @Enumerated(EnumType.STRING)
        var userType : UserType,

        @OneToOne
        @JoinColumn(name = "translator_id")
        var translator : Translator,

        @OneToOne
        @JoinColumn(name = "autoTranslate_id")
        var autoTranslate: AutoTranslate,

        @OneToOne
        @JoinColumn(name = "child_id", nullable = true)
        var child : Child,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id : Long = -1
}