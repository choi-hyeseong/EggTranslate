package com.example.demo.service

import com.example.demo.common.response.Response
import com.example.demo.component.translate.GoogleTranslator
import com.example.demo.dto.translate.TranslateRequestDTO
import com.example.demo.dto.translate.TranslateResponseDTO
import org.springframework.stereotype.Service

@Service
class TranslateService(private val translator: GoogleTranslator) {

    suspend fun translate(requestDTO: TranslateRequestDTO): Response<TranslateResponseDTO> {
        val response = translator.translate(requestDTO)
        return if (response.isSuccess)
            Response.ofSuccess(null, response)
        else
            Response.ofFailure(null, response)
    }

    suspend fun translate(requestDTO: List<TranslateRequestDTO>): Response<List<TranslateResponseDTO>> {
        val response = translator.translate(requestDTO)
        return if (response.all { it.isSuccess })
            Response.ofSuccess(null, response)
        else
            Response.ofFailure(null, response) //하나라도 성공한게 없으면
    }
}