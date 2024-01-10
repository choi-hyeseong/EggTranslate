package com.example.demo.translate.component.google

import com.example.demo.translate.component.Translator
import com.example.demo.translate.dto.TranslateRequestDTO
import com.example.demo.translate.dto.TranslateResponseDTO
import com.google.cloud.translate.Translate
import com.google.cloud.translate.Translation
import org.springframework.stereotype.Component

@Component
class GoogleTranslator(private val translate: Translate) : Translator {

    override suspend fun translate(requestDTO: TranslateRequestDTO): TranslateResponseDTO {
        val result: Translation = translate.translate(
            requestDTO.content,
            Translate.TranslateOption.sourceLanguage(requestDTO.from),
            Translate.TranslateOption.targetLanguage(requestDTO.to),
            Translate.TranslateOption.format("text") //개행 문자 보존
        )
        return TranslateResponseDTO(true, requestDTO.from, requestDTO.to, requestDTO.content, result.translatedText)
    }

//    override suspend fun translate(requestDTO: TranslateRequestDTO): TranslateResponseDTO {
//        val response = webClient.request(requestDTO)
//        return response?.toResponseDTO() ?: TranslateResponseDTO.empty()
//    }
}