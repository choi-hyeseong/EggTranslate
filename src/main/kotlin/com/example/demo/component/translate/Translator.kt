package com.example.demo.component.translate

import com.example.demo.dto.translate.TranslateRequestDTO
import com.example.demo.dto.translate.TranslateResponseDTO
import com.example.demo.response.ObjectResponse

interface Translator {

    suspend fun translate(requestDTO: TranslateRequestDTO) : ObjectResponse<TranslateResponseDTO>
}