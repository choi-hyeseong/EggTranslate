package com.example.demo.translate.component

import com.example.demo.translate.dto.AutoTranslateRequestDTO
import com.example.demo.translate.dto.AutoTranslateResponseDTO

interface Translator {

    suspend fun translate(requestDTO: AutoTranslateRequestDTO) : AutoTranslateResponseDTO

    suspend fun translate(requestDTO: List<AutoTranslateRequestDTO>) : List<AutoTranslateResponseDTO> {
        return requestDTO.map { translate(it) }.toList()
    }
}