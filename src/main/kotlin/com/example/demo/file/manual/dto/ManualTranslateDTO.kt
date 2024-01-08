package com.example.demo.file.manual.dto

import com.example.demo.file.auto.entity.AutoTranslate

class ManualTranslateDTO(
        val translate_content : String,
        val autoTranslate : AutoTranslate,
        val translateRequest : TranslateRequest
) {
}