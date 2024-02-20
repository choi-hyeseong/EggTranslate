package com.example.demo.component.web.azure

import com.azure.ai.formrecognizer.documentanalysis.DocumentAnalysisAsyncClient
import com.azure.ai.formrecognizer.documentanalysis.DocumentAnalysisClient
import com.azure.ai.formrecognizer.documentanalysis.models.AnalyzeResult
import com.azure.ai.formrecognizer.documentanalysis.models.OperationResult
import com.azure.core.exception.HttpRequestException
import com.azure.core.util.polling.AsyncPollResponse
import com.azure.core.util.polling.LongRunningOperationStatus
import com.azure.core.util.polling.PollResponse
import com.azure.core.util.polling.PollerFlux
import com.azure.core.util.polling.SyncPoller
import com.example.demo.ocr.component.ocr.azure.AzureClient
import com.example.demo.ocr.component.ocr.azure.dto.AzureResponseDTO
import com.example.demo.ocr.exception.AzureRequestException
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import kotlinx.coroutines.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import org.springframework.web.multipart.MultipartFile
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.Duration
import java.util.concurrent.TimeoutException
import java.util.function.Function

/**
 * 기존 Sync방식 테스트 클래스라 일단 Async 으로 변경하긴 했음
 */
class AzureClientTest {

    //client init
    val documentClient: DocumentAnalysisAsyncClient = mockk()
    val paramFile: MultipartFile = mockk()
    val azureClient = AzureClient(documentClient)

    //PollerFlux 전용 설정 (pollingInterval등을 위한 가짜 poller)
    val mockPoller: PollerFlux<OperationResult, AnalyzeResult> = mockk()

    //실제 flatMap 처리등을 위한 response
    val response: AsyncPollResponse<OperationResult, AnalyzeResult> = mockk()
    //mockPoller의 timeout이 설정되면 실제 처리될 poller. mockPoller를 사용하기엔 Flux의 모든 메소드를 구현해야함.
    val poller : Flux<AsyncPollResponse<OperationResult, AnalyzeResult>> = Flux.just(response)

    init {
        every { paramFile.bytes } returns byteArrayOf()
        every { mockPoller.setPollInterval(any()) } returns mockPoller
        //timeout 설정시 flux의 형태로 변경됨 (실제 Flux로 변경)
        every { mockPoller.timeout(any()) } returns poller
        every { documentClient.beginAnalyzeDocument(any(), any()) } returns mockPoller
    }

    //만약 status가 Fail 이면 request 실패 여부를 확인하는 테스트 코드
    @Test
    fun TEST_THROWN_FAILED() {
        every { response.status } returns LongRunningOperationStatus.FAILED
        every { response.finalResult } returns mockk()
        val throws = assertThrows(AzureRequestException::class.java) {
            runBlocking {
                azureClient.requestImage(paramFile)
            }
        }
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, throws.code)
    }

    //파싱은 되었으나 내부값이 비어있는경우.
    @Test
    fun TEST_THROWN_EMPTY() {
        val analyzeResult: AnalyzeResult = mockk()
        val finalResult: Mono<AnalyzeResult> = Mono.just(analyzeResult)

        every { analyzeResult.paragraphs } returns null
        every { response.status } returns LongRunningOperationStatus.SUCCESSFULLY_COMPLETED
        every { response.finalResult } returns finalResult

        val throws = assertThrows(AzureRequestException::class.java) {
            runBlocking {
                azureClient.requestImage(paramFile)
            }
        }
        assertEquals(HttpStatus.BAD_REQUEST, throws.code)
    }

    //시간초과가 발생한경우
    @Test
    fun TEST_THROWN_TIMEOUT() {
        every { response.finalResult } throws RuntimeException(TimeoutException())

        val throws = assertThrows(AzureRequestException::class.java) {
            runBlocking {
                azureClient.requestImage(paramFile)
            }
        }
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, throws.code)
    }

    @Test
    fun TEST_THROWN_RATE_LIMIT() {
        val message = "RATE_LIMIT"
        every { response.finalResult } throws HttpRequestException(message, mockk())
        val throws = assertThrows(AzureRequestException::class.java) {
            runBlocking {
                azureClient.requestImage(paramFile)
            }
        }
        assertEquals(message, throws.message)
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, throws.code)
    }
}