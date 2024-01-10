package com.example.demo.translate.entity

import com.example.demo.translate.type.TranslateState
import com.example.demo.user.translator.entity.Translator
import jakarta.persistence.*

//사실상 request entity
@Entity
class ManualResult(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = -1,

//    @OneToOne(cascade = [CascadeType.ALL])
//    @JoinColumn(name = "translateResult_id")
//    var translateResult: TranslateResult,
    // 양방향 참조로 인한 순환 참조 발생. 어처피 접근은 ManualResult에서 자신의 translator id로 findall
    // 이후 찾아온 manual result의 id로 result를 찾아서 하면됨.

    @Enumerated(EnumType.STRING)
    var status : TranslateState,

    @ManyToOne
    @JoinColumn(name = "translator_id")
    var translator : Translator,

    @OneToMany(cascade = [CascadeType.ALL])
    @JoinColumn(name = "manualTranslate_id")
    var manualTranslate: MutableList<ManualTranslate> = mutableListOf()
)