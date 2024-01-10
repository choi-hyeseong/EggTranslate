package com.example.demo.translate.entity

import com.example.demo.translate.type.TranslateState
import com.example.demo.user.basic.entity.User
import com.example.demo.user.basic.type.UserType
import com.example.demo.user.parent.child.entity.Child
import com.example.demo.user.translator.entity.Translator
import jakarta.persistence.*

@Entity
class ManualRequest(

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id : Long = -1,

        @OneToOne
        @JoinColumn(name = "user_id")
        var user : User,

        @Enumerated(EnumType.STRING)
        var status : TranslateState,

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
        var child : Child?,
) {

}