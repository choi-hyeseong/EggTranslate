package com.example.demo.translate.web.service

import com.example.demo.translate.web.component.google.GoogleTranslator
import com.example.demo.translate.web.dto.TranslateRequestDTO
import com.example.demo.translate.web.dto.TranslateResponseDTO
import org.springframework.stereotype.Service

@Service
class WebTranslateService(private val translator: GoogleTranslator) {

    suspend fun translateContent(requestDTO: TranslateRequestDTO): TranslateResponseDTO {
        return translator.translate(requestDTO)
    }

    suspend fun translateContent(requestDTO: List<TranslateRequestDTO>): List<TranslateResponseDTO> {
        return translator.translate(requestDTO)
    }
}