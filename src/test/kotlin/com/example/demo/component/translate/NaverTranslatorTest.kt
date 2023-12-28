package com.example.demo.component.translate

import com.example.demo.component.web.NaverWebClient
import com.example.demo.dto.translate.NaverResponseDTO
import com.example.demo.dto.translate.TranslateRequestDTO
import com.example.demo.dto.translate.TranslateResponseDTO
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.Mockito

class NaverTranslatorTest {


    @Test
    @DisplayName("성공값 체크")
    fun TEST_REQUEST_SUCCESS() {
        val converted = "안녕하세요"
        val target = "ko"
        val naverWebClient = mockk<NaverWebClient>()
        val translator = NaverTranslator(naverWebClient)
        val dto = mockk<NaverResponseDTO>()
        coEvery { naverWebClient.request(any()) } returns dto
        every { dto.toResponseDTO() } returns TranslateResponseDTO(true,"", target, "", converted)
        runBlocking {
            val response = translator.translate(TranslateRequestDTO("ko", target, "asdf"))
            assertTrue(response.isSuccess)
            assertEquals(converted, response.result)
        }
    }


    @Test
    @DisplayName("빈값 체크")
    fun TEST_REQUEST_EMPTY() {
        val naverWebClient = mockk<NaverWebClient>()
        val translator = NaverTranslator(naverWebClient)
        coEvery { naverWebClient.request(any()) } returns null
        runBlocking {
            val response = translator.translate(TranslateRequestDTO("ko", "ko", "asdf"))
            assertFalse(response.isSuccess)
        }
    }
}