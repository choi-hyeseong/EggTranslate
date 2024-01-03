package com.example.demo.component.web.azure

import com.azure.ai.formrecognizer.documentanalysis.DocumentAnalysisClient
import com.azure.ai.formrecognizer.documentanalysis.models.AnalyzeResult
import com.azure.ai.formrecognizer.documentanalysis.models.OperationResult
import com.azure.core.util.BinaryData
import com.azure.core.util.polling.LongRunningOperationStatus
import com.azure.core.util.polling.PollResponse
import com.azure.core.util.polling.SyncPoller
import com.example.demo.exception.AzureRequestException
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import java.time.Duration
import java.util.regex.Pattern
import kotlin.jvm.Throws

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
        val request : SyncPoller<OperationResult, AnalyzeResult> = documentAnalysis.beginAnalyzeDocument("prebuilt-layout", documentData)
        try {
            val response : PollResponse<OperationResult> = request.waitForCompletion(Duration.ofSeconds(timeout)) // 30초 대기
            val status : LongRunningOperationStatus = response.status
            if (status == LongRunningOperationStatus.FAILED)
                throw AzureRequestException("failed to request.")
            else
                return parseParagraph(request.finalResult).joinToString(separator = "\n")
        }
        catch (e : Exception) {
            throw AzureRequestException(e.localizedMessage)
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