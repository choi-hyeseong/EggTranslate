package com.example.demo.translate.dto

import com.example.demo.translate.entity.AutoTranslate
import com.example.demo.translate.entity.TranslationRequest

class TranslateResultDTO(
        val translate_content : String,
        val autoTranslate : AutoTranslate,
        val translationRequest : TranslationRequest
) {
}