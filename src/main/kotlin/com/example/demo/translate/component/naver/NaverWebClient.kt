package com.example.demo.translate.component.naver

import com.example.demo.translate.dto.NaverResponseDTO
import com.example.demo.translate.dto.AutoTranslateRequestDTO
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.reactive.function.client.WebClient
import java.net.URLEncoder


class NaverWebClient {

    @Value("\${naver.client-id}")
    private lateinit var clientID : String //String형은 Properties에서 ""로 감쌀 필요 X

    @Value("\${naver.client-secret}")
    private lateinit var clientSecret : String

    suspend fun request(requestDTO: AutoTranslateRequestDTO) : NaverResponseDTO? {
        val client = WebClient.builder().baseUrl("https://openapi.naver.com/v1/papago/n2mt").build()
        val response = client.post().uri { builder ->
            builder.queryParam("source", requestDTO.from)
                    .queryParam("target", requestDTO.to)
                    .queryParam("text", URLEncoder.encode(requestDTO.content, Charsets.UTF_8)) //url decode
                    .build()
        }
                .header("X-Naver-Client-Id", clientID)
                .header("X-Naver-Client-Secret", clientSecret)
                .header("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                .retrieve()
                .bodyToMono(NaverResponseDTO::class.java)
                .doOnSuccess { it.origin = requestDTO.content }
                .block()
        return response
    }
}