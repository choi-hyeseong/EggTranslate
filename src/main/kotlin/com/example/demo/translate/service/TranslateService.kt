package com.example.demo.translate.service

import com.example.demo.file.service.FileService
import com.example.demo.translate.dto.*
import com.example.demo.translate.exception.TranslateException
import com.example.demo.user.basic.dto.UserDto
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TranslateService(
    private val autoTranslateService: AutoTranslateService,
    private val fileService: FileService,
    private val webTranslateService: WebTranslateService
) {

    @Transactional
    suspend fun translate(userDto: UserDto, response: List<TranslateFIleResponseDTO>): AutoTranslateResponseDTO {
        val fileDtoList = response.map { mapFileDTO(it.fileId, userDto, it) }.toMutableList()

        val saveResponse = autoTranslateService.saveAllTranslate(fileDtoList)
        if (saveResponse.any { it == -1L})
            throw TranslateException("번역 데이터가 정상적으로 저장되지 않았습니다.")

        val autoDTO = AutoTranslateDTO(-1, userDto, autoTranslateService.findAllTranslateFiles(saveResponse))
        val response = autoTranslateService.saveAutoTranslate(autoDTO)

        val result = autoTranslateService.findAutoTranslate(response)
        return AutoTranslateResponseDTO(result.id, result.translateFile.map { TranslateFileResultDTO(it) }.toList())

    }

    suspend fun mapFileDTO(fileId : Long, userDto: UserDto, responseDTO: TranslateFIleResponseDTO) : TranslateFileDTO {
        return TranslateFileDTO(-1, fileService.findFileById(fileId), responseDTO.origin ?: "", responseDTO.result ?: "", responseDTO.from, responseDTO.target)
    }

    //웹 요청이기 때문에 Transactional 필요 없음
    suspend fun requestWebTranslate(requestDTO: TranslateFileRequestDTO) : TranslateFIleResponseDTO {
        val request = TranslateRequestDTO(requestDTO.from, requestDTO.to, requestDTO.content)
        val response = webTranslateService.translateContent(request)
        return TranslateFIleResponseDTO(response.isSuccess, requestDTO.fileId, response.from, response.target, response.origin, response.result)
    }

    suspend fun requestWebTranslate(requestDTO: List<TranslateFileRequestDTO>) : List<TranslateFIleResponseDTO> {
        return coroutineScope {
            requestDTO.map {
                async {
                    requestWebTranslate(it)
                }
            }.toList().awaitAll()
        }
    }


}