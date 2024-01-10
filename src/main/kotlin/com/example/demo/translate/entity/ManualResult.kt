package com.example.demo.translate.entity

import com.example.demo.translate.type.TranslateState
import com.example.demo.user.translator.entity.Translator
import jakarta.persistence.*

@Entity
class ManualResult(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = -1,

    @OneToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "translateresult_id")
    var translateResult: TranslateResult,

    @Enumerated(EnumType.STRING)
    var status : TranslateState,

    @OneToOne
    @JoinColumn(name = "translator_id")
    var translator : Translator,

    @OneToMany(mappedBy = "manualResult", cascade = [CascadeType.ALL])
    var manualTranslate: MutableList<ManualTranslate> = mutableListOf()
)