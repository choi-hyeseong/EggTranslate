package com.example.demo.ocr.component.ocr.azure

import com.azure.ai.formrecognizer.documentanalysis.DocumentAnalysisClient
import com.azure.ai.formrecognizer.documentanalysis.models.AnalyzeResult
import com.azure.ai.formrecognizer.documentanalysis.models.OperationResult
import com.azure.core.util.BinaryData
import com.azure.core.util.polling.LongRunningOperationStatus
import com.azure.core.util.polling.PollResponse
import com.azure.core.util.polling.SyncPoller
import com.example.demo.ocr.exception.AzureRequestException
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import java.time.Duration
import java.util.regex.Pattern

@Component
class AzureClient(private val documentAnalysis : DocumentAnalysisClient) {

    private val timeout : Long = 30
    @Value("\${ocr.ignore_unnecessary}")
    private var ignoreUnnecessaryData : Boolean = false
    private val pattern : Pattern = Pattern.compile(".*[ㄱ-ㅎㅏ-ㅣ가-힣]+.*")

    @Throws
    suspend fun requestImage(file : MultipartFile) : String {
        val documentData = BinaryData.fromBytes(file.bytes)
        //stream이 아닌 byte array로 받아와서 여러군데서 써도 문제 없음
        try {
            val request : SyncPoller<OperationResult, AnalyzeResult> = documentAnalysis.beginAnalyzeDocument("prebuilt-layout", documentData)
            val response : PollResponse<OperationResult> = request.waitForCompletion(Duration.ofSeconds(timeout)) // 30초 대기
            val status : LongRunningOperationStatus = response.status
            if (status == LongRunningOperationStatus.FAILED)
                throw AzureRequestException(HttpStatus.INTERNAL_SERVER_ERROR, "failed to request.")
            else if (request.finalResult.paragraphs.isNullOrEmpty())
                throw AzureRequestException(HttpStatus.BAD_REQUEST, "인식된 텍스트가 없습니다.")
            else
                return parseParagraph(request.finalResult).joinToString(separator = "\n")
        }
        catch (e : Exception) {
            //왜 TimeoutException이 안걸리는지 의문. -> RuntimeException 내에 Timeout except가 호출됨.
            //try문 내에서 throw한게 다 잡힘. -> Timeout만 잡게 수정
            if (e is AzureRequestException)
                throw e
            else
                throw AzureRequestException(HttpStatus.INTERNAL_SERVER_ERROR, e.localizedMessage)
        }

    }

    private fun parseParagraph(result : AnalyzeResult) : List<String> {
        return result.paragraphs.map { paragraph -> paragraph.content }.filter {
            if (ignoreUnnecessaryData)
                pattern.matcher(it).matches()
            else
                true
        }.toList()
    }
}