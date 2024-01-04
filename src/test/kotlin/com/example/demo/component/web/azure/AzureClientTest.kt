package com.example.demo.component.web.azure

import com.azure.ai.formrecognizer.documentanalysis.DocumentAnalysisClient
import com.azure.ai.formrecognizer.documentanalysis.models.AnalyzeResult
import com.azure.ai.formrecognizer.documentanalysis.models.OperationResult
import com.azure.core.exception.HttpRequestException
import com.azure.core.util.polling.LongRunningOperationStatus
import com.azure.core.util.polling.PollResponse
import com.azure.core.util.polling.SyncPoller
import com.example.demo.ocr.component.ocr.azure.AzureClient
import com.example.demo.ocr.exception.AzureRequestException
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import org.springframework.web.multipart.MultipartFile
import java.util.concurrent.TimeoutException

class AzureClientTest {

    val documentClient: DocumentAnalysisClient = mockk()
    val paramFile: MultipartFile = mockk()
    val azureClient = AzureClient(documentClient)
    val poller: SyncPoller<OperationResult, AnalyzeResult> = mockk()
    val response: PollResponse<OperationResult> = mockk()

    init {
        every { paramFile.bytes } returns byteArrayOf()
    }

    @Test
    fun TEST_THROWN_FAILED() {
        val documentClient: DocumentAnalysisClient = mockk()

        val azureClient = AzureClient(documentClient)
        val poller: SyncPoller<OperationResult, AnalyzeResult> = mockk()
        val response: PollResponse<OperationResult> = mockk()
        every { documentClient.beginAnalyzeDocument(any(), any()) } returns poller
        every { poller.waitForCompletion(any()) } returns response
        every { response.status } returns LongRunningOperationStatus.FAILED

        val throws = assertThrows(AzureRequestException::class.java) {
            runBlocking {
                azureClient.requestImage(paramFile)
            }
        }
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, throws.code)
    }

    @Test
    fun TEST_THROWN_EMPTY() {
        val finalResult: AnalyzeResult = mockk()
        every { finalResult.paragraphs } returns null
        every { documentClient.beginAnalyzeDocument(any(), any()) } returns poller
        every { poller.waitForCompletion(any()) } returns response
        every { response.status } returns LongRunningOperationStatus.SUCCESSFULLY_COMPLETED
        every { poller.finalResult } returns finalResult
        val throws = assertThrows(AzureRequestException::class.java) {
            runBlocking {
                azureClient.requestImage(paramFile)
            }
        }
        assertEquals(HttpStatus.BAD_REQUEST, throws.code)
    }

    @Test
    fun TEST_THROWN_TIMEOUT() {
        val message = "MESSAGE"
        every { documentClient.beginAnalyzeDocument(any(), any()) } returns poller
        every { poller.waitForCompletion(any()) } throws RuntimeException(TimeoutException(message))
        //Timeout Exception이 발생하는데 RuntimeException에 의해 wrapping되어 catch 안되는 문제 확인

        val throws = assertThrows(AzureRequestException::class.java) {
            runBlocking {
                azureClient.requestImage(paramFile)
            }
        }
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, throws.code)
        assertTrue(throws.localizedMessage.contains(message))
    }

    @Test
    fun TEST_THROWN_RATE_LIMIT() {
        val message = "MESSAGE"
        every { documentClient.beginAnalyzeDocument(any(), any()) } throws HttpRequestException(message, mockk())
        val throws = assertThrows(AzureRequestException::class.java) {
            runBlocking {
                azureClient.requestImage(paramFile)
            }
        }
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, throws.code)
        assertTrue(throws.localizedMessage.contains(message))
    }
}