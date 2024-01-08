package com.example.demo.translate.dto

import com.example.demo.translate.entity.AutoTranslate
import com.example.demo.translate.entity.TranslateRequest

class TranslateResultDTO(
        val translate_content : String,
        val autoTranslate : AutoTranslate,
        val translateRequest : TranslateRequest
) {
}