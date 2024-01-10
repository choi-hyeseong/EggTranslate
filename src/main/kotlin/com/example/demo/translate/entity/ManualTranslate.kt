package com.example.demo.translate.entity

import jakarta.persistence.*

@Entity
class ManualTranslate (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id : Long,

    @OneToOne
    @JoinColumn(name = "translatefile_id")
    val translateFile: TranslateFile,

    @Column(nullable = false)
    var translateContent: String,

    @ManyToOne
    @JoinColumn(name = "manualResult_id") //변수이름과 동일하게 (manualResult로 지었으니 그렇게 적어야함)
    var manualResult: ManualResult

)