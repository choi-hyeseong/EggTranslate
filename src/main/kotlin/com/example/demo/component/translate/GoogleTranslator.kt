package com.example.demo.component.translate

import com.example.demo.component.web.GoogleWebClient
import com.example.demo.dto.translate.TranslateRequestDTO
import com.example.demo.dto.translate.TranslateResponseDTO
import com.example.demo.response.ObjectResponse
import org.springframework.stereotype.Component

@Component
class GoogleTranslator(private val webClient: GoogleWebClient) : Translator {
    override suspend fun translate(requestDTO: TranslateRequestDTO): ObjectResponse<TranslateResponseDTO> {
        val response = webClient.request(requestDTO)
        return if (response == null)
            ObjectResponse(false, TranslateResponseDTO.empty())
        else
            ObjectResponse(true, response.toResponseDTO())
    }
}