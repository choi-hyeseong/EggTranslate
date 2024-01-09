package com.example.demo.translate.service

import com.example.demo.file.service.FileService
import com.example.demo.translate.component.google.GoogleTranslator
import com.example.demo.translate.dto.*
import com.example.demo.translate.exception.TranslateException
import com.example.demo.user.basic.dto.UserDto
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TranslateService(
    private val translator: GoogleTranslator,
    private val autoTranslateService: AutoTranslateService,
    private val fileService: FileService
) {

    @Transactional
    suspend fun translate(userDto: UserDto, requestDTO: List<AutoTranslateRequestDTO>): AutoTranslateResultDTO {

        // TODO decouple translate (번역 이후 Transaction 되게)
        val saveFiles = requestDTO
            .map { translateContent(it) } //request를 번역하여 Response로 Mapping
            .map {//이 response를 File과 1대1 매핑되는 TranslateFile로 매핑
                TranslateFileDTO(
                    -1,
                    fileService.getFile(userDto.id, it.fileId),
                    it.origin ?: "",
                    it.result ?: "",
                    it.from,
                    it.target
                )
            }
            .map { autoTranslateService.saveTranslate(it) } //이를 DB에 저장하여 id로 매핑
            .toList() //리스트화
        if (saveFiles.any { it == -1L })
            throw TranslateException("번역 데이터가 정상적으로 저장되지 않았습니다.")

        val id = autoTranslateService.saveAutoTranslate(AutoTranslateDTO(
            -1,
            userDto,
            saveFiles.map { autoTranslateService.findTranslateFile(it)}.toMutableList()
        ))

        val result = autoTranslateService.findAutoTranslate(id)
        return AutoTranslateResultDTO(result.id, result.translateFile.map { TranslateFileResultDTO(it) }.toList())


    }

    suspend fun translateContent(requestDTO: AutoTranslateRequestDTO): AutoTranslateResponseDTO {
        return translator.translate(requestDTO)
    }

    suspend fun translateContent(requestDTO: List<AutoTranslateRequestDTO>): List<AutoTranslateResponseDTO> {
        return translator.translate(requestDTO)
    }
}