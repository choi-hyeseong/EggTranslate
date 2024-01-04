package com.example.demo.translate.component.naver

import com.example.demo.translate.component.Translator
import com.example.demo.translate.dto.TranslateRequestDTO
import com.example.demo.translate.dto.TranslateResponseDTO

class NaverTranslator(private val naverWebClient: NaverWebClient) : Translator {
    // 테스트 가능성을 위한 WebClient 분리
    // https://velog.io/@ililil9482/%ED%85%8C%EC%8A%A4%ED%8A%B8-%EC%BD%94%EB%93%9C-%EC%9E%91%EC%84%B1 참조
    override suspend fun translate(requestDTO: TranslateRequestDTO): TranslateResponseDTO {
        val response = naverWebClient.request(requestDTO)
        return response?.toResponseDTO() ?: TranslateResponseDTO.empty()
    }
}