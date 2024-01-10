package com.example.demo.translate.entity

import com.example.demo.user.basic.entity.User
import com.example.demo.user.basic.type.UserType
import com.example.demo.user.parent.child.entity.Child
import jakarta.persistence.*

@Entity
class TranslateResult(

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id : Long = -1,

        @OneToOne
        @JoinColumn(name = "user_id")
        var user : User,

        @Enumerated(EnumType.STRING)
        var userType : UserType,

        @OneToOne
        @JoinColumn(name = "autoTranslate_id")
        var autoTranslate: AutoTranslate,

        @OneToOne
        @JoinColumn(name = "child_id", nullable = true)
        var child : Child?,
) {

        //처음 결과 생성시 null로 초기화 됨.
        @OneToOne
        @JoinColumn(name = "manualtranslate_id", nullable = true)
        var manualResult: ManualResult? = null

}