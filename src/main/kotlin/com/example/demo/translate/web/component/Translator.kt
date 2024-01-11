package com.example.demo.translate.web.component

import com.example.demo.translate.web.dto.TranslateRequestDTO
import com.example.demo.translate.web.dto.TranslateResponseDTO

interface Translator {

    suspend fun translate(requestDTO: TranslateRequestDTO) : TranslateResponseDTO

    suspend fun translate(requestDTO: List<TranslateRequestDTO>) : List<TranslateResponseDTO> {
        return requestDTO.map { translate(it) }.toList()
    }
}