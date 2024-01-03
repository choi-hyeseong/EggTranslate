package com.example.demo.component.translate

import com.example.demo.dto.translate.TranslateRequestDTO
import com.example.demo.dto.translate.TranslateResponseDTO

interface Translator {

    suspend fun translate(requestDTO: TranslateRequestDTO) : TranslateResponseDTO

    suspend fun translate(requestDTO: List<TranslateRequestDTO>) : List<TranslateResponseDTO> {
        return requestDTO.map { translate(it) }.toList()
    }
}