package com.example.demo.translate.auto.entity

import com.example.demo.file.entity.File
import jakarta.persistence.*

@Entity
class TranslateFile(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = -1,

    @ManyToOne
    @JoinColumn(name = "file_id")
    var file: File,

    @Column(nullable = false, columnDefinition = "TEXT")
    var origin: String,

    @Column(nullable = false, columnDefinition = "TEXT")
    var translate: String,

    @Column(nullable = false, length = 10)
    var fromLang: String,

    @Column(nullable = false, length = 10)
    var toLang: String,

)