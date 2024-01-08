package com.example.demo.file.translated.entity

import jakarta.persistence.*
import java.io.File

@Entity
class TranslateFile (
       @JoinColumn(name = "file_id")
       var file : File,

       @JoinColumn(name = "translate_result_id")
       var translate_result : TranslateResult

) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id : Long = -1
}