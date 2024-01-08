package com.example.demo.file.request.dto

import com.example.demo.file.auto.dto.AutoTranslateDTO
import com.example.demo.user.parent.child.dto.ChildRequestDto
import com.example.demo.user.translator.dto.TranslatorDTO

class TranslateRequestDTO(
        val status : String,
        val translator : TranslatorDTO,
        val autoTranslate : AutoTranslateDTO,
        val child : ChildRequestDto
) {
}