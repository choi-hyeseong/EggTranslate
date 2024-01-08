package com.example.demo.file.auto.dto

import com.example.demo.file.translated.dto.TranslateFileDTO

class AutoTranslateDTO(
        val origin : String,
        val translate : String,
        val from : String,
        val to : String,
        val translate_file : List<TranslateFileDTO>
) {

}