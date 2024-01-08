package com.example.demo.translate.dto

import com.example.demo.user.parent.child.dto.ChildRequestDto
import com.example.demo.user.translator.dto.TranslatorDTO

class RequestDTO(
        val status : String,
        val translator : TranslatorDTO,
        val autoTranslate : AutoTranslateDTO,
        val child : ChildRequestDto
) {
}