package com.example.demo.common.database.converter

import com.example.demo.voca.dto.VocaResponseDTO
import com.fasterxml.jackson.databind.ObjectMapper

class JsonVocaFlatter : AbstractStringFlatter<VocaResponseDTO>() {

    private val objectMapper = ObjectMapper()

    override fun recover(s: String): VocaResponseDTO {
        return objectMapper.readValue(s, VocaResponseDTO::class.java)
    }

    override fun convert(obj: VocaResponseDTO): String {
        return objectMapper.writeValueAsString(obj)
    }
}