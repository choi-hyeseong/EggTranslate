package com.example.demo.component.translate

import com.example.demo.component.web.NaverWebClient
import com.example.demo.dto.translate.NaverResponseDTO
import com.example.demo.dto.translate.TranslateRequestDTO
import com.example.demo.dto.translate.TranslateResponseDTO
import com.example.demo.response.ObjectResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import java.net.URLDecoder

@Component
class NaverTranslator(private val naverWebClient: NaverWebClient) : Translator {
    // 테스트 가능성을 위한 WebClient 분리
    // https://velog.io/@ililil9482/%ED%85%8C%EC%8A%A4%ED%8A%B8-%EC%BD%94%EB%93%9C-%EC%9E%91%EC%84%B1 참조
    override suspend fun translate(requestDTO: TranslateRequestDTO): ObjectResponse<TranslateResponseDTO> {
        val response = naverWebClient.request(requestDTO)
        return if (response == null)
            ObjectResponse(false, TranslateResponseDTO.empty())
        else
            ObjectResponse(true, response.toResponseDTO())
    }
}