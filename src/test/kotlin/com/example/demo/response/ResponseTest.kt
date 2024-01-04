package com.example.demo.response

import com.example.demo.common.response.Response
import com.example.demo.translate.component.Translator
import com.example.demo.translate.dto.TranslateRequestDTO
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class ResponseTest {

    @Test
    @DisplayName("성공_여부_테스트")
    fun TEST_IS_SUCCESS() {
        val response = Response(true, null, object {})
        assertTrue(response.isSuccess)
    }

    @Test
    @DisplayName("실패_여부_테스트")
    fun TEST_IS_FALSE() {
        val response = Response(false, null, object {})
        assertFalse(response.isSuccess)
    }


    @Test
    @DisplayName("결과 반환 테스트")
    fun TEST_RETURN_RESULT() {
        val mockTrans = mockk<Translator>()
        val response = Response(true, null, mockTrans)
        coEvery { mockTrans.translate(any<TranslateRequestDTO>())} returns mockk()
        runBlocking {
            response.data?.translate(TranslateRequestDTO("", "", ""))
            coVerify(atLeast = 1) { mockTrans.translate(any<TranslateRequestDTO>()) }
        }

    }


}