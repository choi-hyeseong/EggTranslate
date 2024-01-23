package com.example.demo.translate.web.service

import com.example.demo.translate.web.component.google.GoogleTranslator
import com.example.demo.translate.web.dto.TranslateRequestDTO
import com.example.demo.translate.web.dto.TranslateResponseDTO
import com.example.demo.translate.web.prehandle.TranslatePreHandler
import org.springframework.stereotype.Service

@Service
class WebTranslateService(private val translator: GoogleTranslator, private val preHandler: TranslatePreHandler) {

    suspend fun translateContent(requestDTO: TranslateRequestDTO): TranslateResponseDTO {
        return translator.translate(requestDTO.apply { content = preHandler.preHandle(to, content) })
    }

    suspend fun translateContent(requestDTO: List<TranslateRequestDTO>): List<TranslateResponseDTO> {
        return translator.translate(requestDTO)
    }
}