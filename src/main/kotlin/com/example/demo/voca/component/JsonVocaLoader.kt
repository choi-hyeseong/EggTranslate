package com.example.demo.voca.component

import com.example.demo.voca.dto.VocaDTO
import com.example.demo.voca.entity.Voca
import com.example.demo.voca.exception.VocaException
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.google.gson.JsonParser
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.io.File
import java.io.FileReader

@Component
class JsonVocaLoader(
    @Value("\${voca-path}")
    private val path: String
) : VocaLoader {

    private val mapper: ObjectMapper = ObjectMapper()

    override suspend fun load(): List<VocaDTO> {
        val file = File(path)
        if (file.exists()) {
            val response = file.inputStream().use {
                mapper.readValue(it, object : TypeReference<List<VocaDTO>>() {})
            }
            return response
        } else
            throw VocaException("단어장 파일이 존재하지 않습니다.")
    }
}