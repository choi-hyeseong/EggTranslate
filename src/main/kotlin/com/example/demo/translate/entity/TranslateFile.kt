package com.example.demo.translate.entity

import com.example.demo.file.entity.File
import jakarta.persistence.*

@Entity
class TranslateFile(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = -1,

    @OneToOne
    @JoinColumn(name = "file_id")
    var file: File,

    @ManyToOne
    @JoinColumn(name = "autotranslate_id")
    var autoTranslate: AutoTranslate

) {

}