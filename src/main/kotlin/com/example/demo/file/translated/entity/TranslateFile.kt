package com.example.demo.file.translated.entity

import jakarta.persistence.*
import java.io.File

@Entity
class TranslateFile (
       @JoinColumn(name = "file_id")
       var file : File,

       @JoinColumn(name = "autoTranslate_id")
       var autoTranslate : AutoTranslate

) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id : Long = -1
}