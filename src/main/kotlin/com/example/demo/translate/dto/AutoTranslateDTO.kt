package com.example.demo.translate.dto

import com.example.demo.file.dto.TranslateFileDTO

class AutoTranslateDTO(
        val origin : String,
        val translate : String,
        val from : String,
        val to : String,
        val translate_file : List<TranslateFileDTO>
) {

}