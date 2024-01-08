package com.example.demo.file.translated.entity

import com.example.demo.file.auto.entity.AutoTranslate
import com.example.demo.file.basic.entity.File
import jakarta.persistence.*

@Entity
class TranslateFile(

    @ManyToOne
    @JoinColumn(name = "file_id")
    var file: File,

    @ManyToOne
    @JoinColumn(name = "autotranslate_id")
    var autoTranslate: AutoTranslate

) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = -1
}