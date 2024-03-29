package com.example.demo.ocr.component.ocr.azure

import com.azure.ai.formrecognizer.documentanalysis.DocumentAnalysisAsyncClient
import com.azure.ai.formrecognizer.documentanalysis.models.AnalyzeResult
import com.azure.ai.formrecognizer.documentanalysis.models.OperationResult
import com.azure.ai.formrecognizer.documentanalysis.models.Point
import com.azure.core.util.BinaryData
import com.azure.core.util.polling.AsyncPollResponse
import com.azure.core.util.polling.LongRunningOperationStatus
import com.example.demo.ocr.component.ocr.azure.dto.AzureResponseDTO
import com.example.demo.ocr.component.ocr.model.Area
import com.example.demo.ocr.component.ocr.model.Paragraph
import com.example.demo.ocr.exception.AzureRequestException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.Duration
import java.util.regex.Pattern

/**
 *   기존 Sync -> Async으로 변경한 이후 속도 50% 향상됨.
 */
@Component
class AzureClient(private val asyncDocumentAnalysis: DocumentAnalysisAsyncClient) {

    private val timeout: Long = 30 //async client는 지정 안되는듯?

    //SyncPoller 기본값이 5초마다 끌어옴.. 0.25초마다 끌어오게 변경
    private val pollIntervalMillis: Long = 250

    @Value("\${ocr.ignore_unnecessary}")
    private var ignoreUnnecessaryData: Boolean = false
    private val pattern: Pattern = Pattern.compile(".*[ㄱ-ㅎㅏ-ㅣ가-힣]+.*")

    @Throws
    suspend fun requestImage(file: MultipartFile): AzureResponseDTO {
        //stream이 아닌 byte array로 받아와서 여러군데서 써도 문제 없음
        val documentData = BinaryData.fromBytes(file.bytes)
        try {
            //AsyncPoller를 가져옴 (PollInterval은 0.25s)
            val request: Flux<AsyncPollResponse<OperationResult, AnalyzeResult>> =
                asyncDocumentAnalysis.beginAnalyzeDocument("prebuilt-layout", documentData)
                    .apply { setPollInterval(Duration.ofMillis(pollIntervalMillis)) }
                    .timeout(Duration.ofSeconds(timeout))
            //결과값을 mapping함 (flatmap으로 평탄화) 언제? : 결과를 받았을때
            val response = parseFluxResponse(request)
            return withContext(Dispatchers.IO) { response.blockFirst()!! } //실제 webflux를 사용하는게 아니므로 최종적인 block
        } catch (e: Exception) {
            //azure 외의 exception은 async exception으로 처리 되서 spring이 해줌
            if (e is AzureRequestException)
                throw e
            else if (e.cause is AzureRequestException)
                throw e.cause as AzureRequestException
            else
                throw AzureRequestException(HttpStatus.INTERNAL_SERVER_ERROR, e.message ?: e.localizedMessage)
        }
    }


    private fun parseFluxResponse(request: Flux<AsyncPollResponse<OperationResult, AnalyzeResult>>): Flux<AzureResponseDTO> {
        //flatmap은 내부 값만 바꿔주지 R 자체로 바꿔주는건 아님 (Flux<R>)
        //그리고 내부의 Mono를 평탄화 해줌 (flatMap) -> Flux<Mono<T>> -> Flux<T>
        return request.flatMap {
            val result = it.finalResult
            val status: LongRunningOperationStatus = it.status
            if (status == LongRunningOperationStatus.FAILED)
            //실패했을경우 Mono의 Error로 Exception을 반환
                Mono.error(AzureRequestException(HttpStatus.INTERNAL_SERVER_ERROR, "failed to request."))
            else
            //성공한경우 Response 파싱. FlatMap이므로 Mono<Mono<~>> 가 아닌 Mono로 가져올 수 있음 (평탄화)
                result.flatMap { res ->
                    if (res.paragraphs.isNullOrEmpty())
                    //문단이 비어있을경우 throw
                        Mono.error(AzureRequestException(HttpStatus.BAD_REQUEST, "인식된 텍스트가 없습니다."))
                    else
                    //성공했을경우 Mono의 just로 Mono로 파싱후 반환
                        Mono.just(AzureResponseDTO(res.content, parseParagraph(res)))
                }
        }
    }

    private fun parseParagraph(result: AnalyzeResult): List<Paragraph> {
        val paragraphList: MutableList<Paragraph> = mutableListOf()
        result.paragraphs.forEach {
            val polygons = it.boundingRegions[0].boundingPolygon
            polygons.sortWith { p1: Point, p2: Point -> (p1.x + p1.y).compareTo((p2.x + p2.y)) }
            paragraphList.add(Paragraph(it.content, Area(polygons[0], polygons[3])))
        }
        return paragraphList
    }
}