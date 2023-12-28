package com.example.demo.response

import com.example.demo.common.response.ObjectResponse
import com.example.demo.component.translate.Translator
import com.example.demo.dto.translate.TranslateRequestDTO
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.Mockito

class ObjectResponseTest {

    @Test
    @DisplayName("성공_여부_테스트")
    fun TEST_IS_SUCCESS() {
        val response = ObjectResponse(true, object {})
        assertTrue(response.isSuccess)
    }

    @Test
    @DisplayName("실패_여부_테스트")
    fun TEST_IS_FALSE() {
        val response = ObjectResponse(false, object {})
        assertFalse(response.isSuccess)
    }


    @Test
    @DisplayName("결과 반환 테스트")
    fun TEST_RETURN_RESULT() {
        val mockTrans = mockk<Translator>()
        val response = ObjectResponse(false, mockTrans)
        coEvery { mockTrans.translate(any<TranslateRequestDTO>())} returns mockk()
        runBlocking {
            response.response.translate(TranslateRequestDTO("", "", ""))
            coVerify(atLeast = 1) { mockTrans.translate(any<TranslateRequestDTO>()) }
        }

    }


}