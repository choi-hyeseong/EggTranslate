package com.example.demo.translate.service

import com.example.demo.convertOrNull
import com.example.demo.docs.service.DocumentService
import com.example.demo.file.service.FileService
import com.example.demo.translate.auto.dto.*
import com.example.demo.translate.auto.service.ManualResultService
import com.example.demo.translate.auto.service.TranslateManageService
import com.example.demo.translate.auto.service.TranslateResultService
import com.example.demo.translate.manual.exception.ManualException
import com.example.demo.translate.exception.TranslateException
import com.example.demo.translate.manual.dto.ManualResultDTO
import com.example.demo.translate.manual.type.TranslateState
import com.example.demo.translate.web.dto.TranslateRequestDTO
import com.example.demo.translate.web.dto.TranslateResponseDTO
import com.example.demo.translate.web.service.WebTranslateService
import com.example.demo.member.user.dto.UserDto
import com.example.demo.member.parent.child.dto.ChildDTO
import com.example.demo.member.translator.dto.TranslatorDTO
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

//웹 번역 (WebTransService)와 저장된 번역 (TranslateResult...)를 연결시켜주는 서비스.
@Service
class TranslateService(
    private val translateResultService: TranslateResultService,
    private val documentService: DocumentService,
    private val fileService: FileService,
    private val webTranslateService: WebTranslateService,
    private val manualResultService: ManualResultService,
    private val translateManageService: TranslateManageService
) {

    @Transactional
    suspend fun saveTranslate(
        userDto: UserDto?,
        childDTO: ChildDTO?,
        response: List<TranslateFileResponseDTO>
    ): TranslateResultResponseDTO {
        val fileDtoList = mapFileDTO(userDto, response)
        val autoDTO = AutoTranslateDTO(null, fileDtoList)

        val resultDTO =
            TranslateResultDTO(null, userDto, autoDTO, childDTO, null) //저장시 Manual Result (번역가 번역 요청은 없음)
        val saveResult = translateResultService.saveTranslateResult(userDto?.id, resultDTO)
            ?: throw TranslateException("번역 결과가 정상적으로 저장되지 않았습니다.")

        return translateResultService.findTranslateResult(saveResult).toResponseDTO()

    }

    @Transactional
    suspend fun request(translatorDTO: TranslatorDTO, resultId: Long): TranslateResultResponseDTO {
        val saveDTO = ManualResultDTO(null, translatorDTO, TranslateState.REQUEST, mutableListOf())
        if (manualResultService.manualResultExists(resultId))
            throw ManualException("이미 요청된 번역 결과입니다. 결과 ID : $resultId")

        val response = translateManageService.saveManualResult(resultId, translatorDTO.id!!, saveDTO)
        if (response == null)
            throw TranslateException("번역 요청 도중 오류가 발생했습니다.")

        return translateResultService.findTranslateResult(resultId).toResponseDTO()
    }

    suspend fun mapFileDTO(
        userDto: UserDto?,
        responseDTO: List<TranslateFileResponseDTO>
    ): MutableList<TranslateFileDTO> {
        return responseDTO.map {
            val fileData = it.translateFileData
            val translateData = it.translateResultData
            val file = fileData.fileId.convertOrNull { id -> fileService.findFileById(id) }
            val document = fileData.documentId.convertOrNull { id -> documentService.findDocumentById(id) }
            TranslateFileDTO(
                null,
                file,
                fileData.convert,
                document,
                fileData.convertDocumentDTO,
                translateData.voca,
                translateData.origin ?: "",
                translateData.result ?: "",
                translateData.from,
                translateData.to
            )
        }.toMutableList()
    }

    //웹 요청이기 때문에 Transactional 필요 없음
    suspend fun requestWebTranslate(requestDTO: TranslateRequestDTO): TranslateResponseDTO {
        val request = TranslateRequestDTO(requestDTO.from, requestDTO.to, requestDTO.content)
        return webTranslateService.translateContent(request)
    }

    suspend fun requestWebTranslate(requestDTO: List<TranslateRequestDTO>): List<TranslateResponseDTO> {
        return coroutineScope {
            requestDTO.map {
                async {
                    requestWebTranslate(it)
                }
            }.toList().awaitAll()
        }
    }


}