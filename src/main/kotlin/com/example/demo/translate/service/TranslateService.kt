package com.example.demo.translate.service

import com.example.demo.file.service.FileService
import com.example.demo.translate.auto.dto.*
import com.example.demo.translate.dto.*
import com.example.demo.translate.manual.exception.ManualException
import com.example.demo.translate.exception.TranslateException
import com.example.demo.translate.manual.dto.ManualResultDTO
import com.example.demo.translate.manual.type.TranslateState
import com.example.demo.translate.web.dto.TranslateRequestDTO
import com.example.demo.translate.web.service.WebTranslateService
import com.example.demo.user.basic.dto.UserDto
import com.example.demo.user.parent.child.dto.ChildDTO
import com.example.demo.user.translator.dto.TranslatorDTO
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TranslateService(
    private val translateDataService: TranslateDataService,
    private val fileService: FileService,
    private val webTranslateService: WebTranslateService
) {

    @Transactional
    suspend fun translate(userDto: UserDto, childDTO: ChildDTO?, response: List<TranslateFileResponseDTO>): TranslateResultResponseDTO {
        val fileDtoList = response.map { mapFileDTO(it.fileId, userDto, it) }.toMutableList()
        val autoDTO = AutoTranslateDTO(-1, userDto, fileDtoList)

        val resultDTO = TranslateResultSaveDTO(-1, userDto, autoDTO, childDTO)
        val saveResult = translateDataService.saveTranslateResult(resultDTO)
        if (saveResult == -1L)
            throw TranslateException("번역 결과가 정상적으로 저장되지 않았습니다.")

        return translateDataService.findTranslateResult(saveResult).toResponseDTO()

    }

    @Transactional
    suspend fun request(userDto: UserDto, translatorDTO: TranslatorDTO, resultId : Long) : TranslateResultResponseDTO {

        val saveDTO = ManualResultDTO(-1, translatorDTO, TranslateState.REQUEST, mutableListOf())
        if (translateDataService.manualResultExists(resultId))
            throw ManualException("이미 요청된 번역 결과입니다. 결과 ID : $resultId")

        val response = translateDataService.saveManualResult(resultId, saveDTO)
        if (response == -1L)
            throw TranslateException("번역 요청 도중 오류가 발생했습니다.")

        return translateDataService.findTranslateResult(resultId).toResponseDTO()

    }

    suspend fun mapFileDTO(fileId : Long, userDto: UserDto, responseDTO: TranslateFileResponseDTO) : TranslateFileDTO {
        return TranslateFileDTO(-1, fileService.findFileById(fileId), responseDTO.origin ?: "", responseDTO.result ?: "", responseDTO.from, responseDTO.target)
    }

    //웹 요청이기 때문에 Transactional 필요 없음
    suspend fun requestWebTranslate(requestDTO: TranslateFileRequestDTO) : TranslateFileResponseDTO {
        val request = TranslateRequestDTO(requestDTO.from, requestDTO.to, requestDTO.content)
        val response = webTranslateService.translateContent(request)
        return TranslateFileResponseDTO(-1, response.isSuccess, requestDTO.fileId, response.from, response.target, response.origin, response.result)
    }

    suspend fun requestWebTranslate(requestDTO: List<TranslateFileRequestDTO>) : List<TranslateFileResponseDTO> {
        return coroutineScope {
            requestDTO.map {
                async {
                    requestWebTranslate(it)
                }
            }.toList().awaitAll()
        }
    }


}