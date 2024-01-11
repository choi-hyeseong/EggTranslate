package com.example.demo.translate.auto.entity

import com.example.demo.translate.manual.entity.ManualResult
import com.example.demo.user.basic.entity.User
import com.example.demo.user.basic.type.UserType
import com.example.demo.user.parent.child.entity.Child
import jakarta.persistence.*

@Entity
class TranslateResult(

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id : Long = -1,

        @ManyToOne
        @JoinColumn(name = "user_id")
        var user : User,

        @Enumerated(EnumType.STRING)
        var userType : UserType,

        @OneToOne(cascade = [CascadeType.ALL])
        @JoinColumn(name = "autoTranslate_id")
        var autoTranslate: AutoTranslate,

        @OneToOne
        @JoinColumn(name = "child_id", nullable = true)
        var child : Child?,
) {

        //처음 결과 생성시 null로 초기화 됨.
        //순환 참조 문제로 단뱡향으로 설정. 단, id를 result에서 갖고 있어 cascade가 가능.
        @OneToOne(cascade = [CascadeType.ALL])
        @JoinColumn(name = "manualResult_id")
        var manualResult: ManualResult? = null

}