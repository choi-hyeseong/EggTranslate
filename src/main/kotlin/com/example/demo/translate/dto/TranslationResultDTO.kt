package com.example.demo.translate.dto

import com.example.demo.translate.entity.AutoTranslate
import com.example.demo.translate.entity.TranslationRequest
import com.example.demo.translate.entity.TranslationResult

class TranslationResultDTO(
        val id : Long = -1,
        val translateContent : String,
        val translationRequest : TranslationRequestDTO
) {

        constructor(translationResult: TranslationResult) : this(translationResult.id, translationResult.translateContent, TranslationRequestDTO(translationResult.translationRequest))
        fun toEntity() : TranslationResult = TranslationResult(id, translateContent, translationRequest.toEntity())

}