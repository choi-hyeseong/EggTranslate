package com.example.demo.translate.component.google

import com.example.demo.translate.dto.GoogleResponseDTO
import com.example.demo.translate.dto.AutoTranslateRequestDTO
import org.springframework.beans.factory.annotation.Value
import org.springframework.util.LinkedMultiValueMap
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.client.WebClient

@Deprecated("Use GoogleTranslation.kt")
class GoogleWebClient {

    @Value("\${rapid.api-key}")
    private lateinit var apiKey: String

    @Value("\${rapid.api-host}")
    private lateinit var apiHost: String

    suspend fun request(requestDTO: AutoTranslateRequestDTO): GoogleResponseDTO? {
        val client = WebClient.builder().baseUrl("https://google-translate113.p.rapidapi.com/api/v1/translator/text").build()
        val body = LinkedMultiValueMap<String, String>().apply {
            add("from", requestDTO.from)
            add("to", requestDTO.to)
            add("text", requestDTO.content)
        }
        val response = client.post().body(BodyInserters.fromFormData(body))
                .header("X-RapidAPI-Key", apiKey)
                .header("X-RapidAPI-Host", apiHost)
                .header("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                .retrieve()
                .bodyToMono(GoogleResponseDTO::class.java)
                .doOnSuccess {
                    it.from = requestDTO.from
                    it.target = requestDTO.to
                    it.origin = requestDTO.content
                }
                .block()
        return response
    }
}