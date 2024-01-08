package com.example.demo.file.entity

import com.example.demo.translate.entity.AutoTranslate
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