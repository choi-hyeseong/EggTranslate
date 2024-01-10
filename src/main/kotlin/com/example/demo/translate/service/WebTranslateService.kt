package com.example.demo.translate.service

import com.example.demo.translate.component.google.GoogleTranslator
import com.example.demo.translate.dto.TranslateRequestDTO
import com.example.demo.translate.dto.TranslateResponseDTO
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