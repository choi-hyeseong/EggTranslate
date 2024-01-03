package com.example.demo.component.web

import com.example.demo.dto.translate.NaverResponseDTO
import com.example.demo.dto.translate.TranslateRequestDTO
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import java.net.URLEncoder

@Component
class NaverWebClient {

    @Value("\${naver.client-id}")
    private lateinit var clientID : String //String형은 Properties에서 ""로 감쌀 필요 X

    @Value("\${naver.client-secret}")
    private lateinit var clientSecret : String

    suspend fun request(requestDTO: TranslateRequestDTO) : NaverResponseDTO? {
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