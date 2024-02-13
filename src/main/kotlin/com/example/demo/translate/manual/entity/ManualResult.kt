package com.example.demo.translate.manual.entity

import com.example.demo.common.database.entity.BaseEntity
import com.example.demo.translate.manual.type.TranslateState
import com.example.demo.user.translator.entity.Translator
import jakarta.persistence.*

//사실상 request entity
@Entity
class ManualResult(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long?,

//    @OneToOne(cascade = [CascadeType.ALL])
//    @JoinColumn(name = "translateResult_id")
//    var translateResult: TranslateResult,
    // 양방향 참조로 인한 순환 참조 발생. 어처피 접근은 ManualResult에서 자신의 translator id로 findall
    // 이후 찾아온 manual result의 id로 result를 찾아서 하면됨.

    @Enumerated(EnumType.STRING)
    var status : TranslateState,

    @ManyToOne
    @JoinColumn(name = "translator_id")
    var translator : Translator?,

    //Result 기준으로 join 해야함.
    @OneToMany(cascade = [CascadeType.ALL])
    @JoinColumn(name = "manualResult_id")
    var manualTranslate: MutableList<ManualTranslate> = mutableListOf()
) : BaseEntity()