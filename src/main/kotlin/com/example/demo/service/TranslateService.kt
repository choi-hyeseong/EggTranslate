package com.example.demo.service

import com.example.demo.component.translate.GoogleTranslator
import com.example.demo.component.translate.NaverTranslator
import com.example.demo.dto.translate.TranslateRequestDTO
import com.example.demo.dto.translate.TranslateResponseDTO
import com.example.demo.response.ObjectResponse
import org.springframework.stereotype.Service

@Service
class TranslateService(private val translator: GoogleTranslator) {

    suspend fun translate(requestDTO: TranslateRequestDTO): ObjectResponse<TranslateResponseDTO> {
        return translator.translate(requestDTO)
    }
}