package com.example.demo.file.manual.dto

import com.example.demo.file.auto.entity.AutoTranslate
import com.example.demo.file.request.entity.TranslateRequest

class ManualTranslateDTO(
        val translate_content : String,
        val autoTranslate : AutoTranslate,
        val translateRequest : TranslateRequest
) {
}