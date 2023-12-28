package com.example.demo.service

import com.example.demo.component.translate.GoogleTranslator
import com.example.demo.component.translate.NaverTranslator
import com.example.demo.dto.translate.TranslateRequestDTO
import com.example.demo.dto.translate.TranslateResponseDTO
import com.example.demo.common.response.ObjectResponse
import org.springframework.stereotype.Service

@Service
class TranslateService(private val translator: NaverTranslator) {

    suspend fun translate(requestDTO: TranslateRequestDTO): ObjectResponse<TranslateResponseDTO> {
        val response = translator.translate(requestDTO)
        return if (response.isSuccess)
            ObjectResponse(true, response)
        else
            ObjectResponse(false, response)
    }

    suspend fun translate(requestDTO: List<TranslateRequestDTO>): ObjectResponse<List<TranslateResponseDTO>> {
        val response = translator.translate(requestDTO)
        return if (response.all { it.isSuccess })
            ObjectResponse(true, response)
        else
            ObjectResponse(false, response) //하나라도 성공한게 없으면
    }
}