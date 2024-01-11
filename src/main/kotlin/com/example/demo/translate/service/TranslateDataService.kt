package com.example.demo.translate.service

import com.example.demo.translate.dto.*
import com.example.demo.translate.exception.ManualException
import com.example.demo.translate.exception.TranslateException
import com.example.demo.translate.repository.*
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TranslateDataService(
    private val translateFileRepository: TranslateFileRepository,
    private val autoTranslateRepository: AutoTranslateRepository,
    private val translateResultRepository: TranslateResultRepository,
    private val manualResultRepository: ManualResultRepository,
    private val manualTranslateRepository: ManualTranslateRepository
) {

    @Transactional
    fun saveTranslate(translateFileDTO: TranslateFileDTO): Long {
        return translateFileRepository.save(translateFileDTO.toEntity()).id
    }

    @Transactional
    suspend fun saveAllTranslate(translateFileDTO: List<TranslateFileDTO>): List<Long> {
        return translateFileRepository.saveAll(translateFileDTO.map { it.toEntity() }).map { it.id }
    }

    @Transactional(readOnly = true)
    fun findTranslateFile(translateFileId: Long): TranslateFileDTO {
        return TranslateFileDTO(
            translateFileRepository.findById(translateFileId)
                .orElseThrow { TranslateException("번역 데이터가 존재하지 않습니다.") })
    }

    @Transactional(readOnly = true)
    suspend fun findAllTranslateFiles(translateFileId: List<Long>): MutableList<TranslateFileDTO> {
        return translateFileRepository.findAllById(translateFileId).map {
            TranslateFileDTO(it)
        }.toMutableList()
    }


    @Transactional
    suspend fun saveAutoTranslate(autoTranslateDTO: AutoTranslateDTO): Long {
        return autoTranslateRepository.save(autoTranslateDTO.toEntity()).id
    }


    @Transactional
    suspend fun saveTranslateResult(translateResultDTO: TranslateResultSaveDTO): Long {
        return translateResultRepository.save(translateResultDTO.toEntity()).id
    }

    @Transactional
    suspend fun findTranslateResult(id: Long): TranslateResultDTO {
        return TranslateResultDTO(
            translateResultRepository.findById(id).orElseThrow { TranslateException("존재 하지 않는 번역 결과입니다.") }
        )
    }

    @Transactional
    suspend fun findAllTranslateResultByUserId(id: Long): List<TranslateResultDTO> {
        return translateResultRepository.findAllByUserId(id).map {
            TranslateResultDTO(it)
        }.toMutableList()
    }


    @Transactional
    suspend fun findAutoTranslate(id: Long): AutoTranslateDTO {
        return AutoTranslateDTO(
            autoTranslateRepository.findById(id).orElseThrow { TranslateException("존재 하지 않는 번역 내용입니다.") }
        )
    }

    @Transactional
    suspend fun saveManualResult(resultId: Long, resultDTO: ManualResultDTO): Long {
        val response = translateResultRepository.save(findTranslateResult(resultId).apply {
            manualResultDTO = resultDTO
        }.toEntity())

        return response.id
    }

    // TODO manual translate 확인하기
    @Transactional
    suspend fun update(resultId: Long, translateFileId: Long, content: String) {
        val response = findTranslateResult(resultId)

        val translateFiles = response.autoTranslate.translateFile

        if (translateFiles.none { it.id == translateFileId })
            throw ManualException("해당 번역 요청에 포함되지 않은 자동 번역 데이터입니다. 결과 ID : $resultId, 자동번역 ID : $translateFileId")

        if (response.manualResultDTO == null)
            throw ManualException("생성되지 않은 번역 요청입니다. 결과 ID : $resultId")

        if (existManualTranslateByTranslateFileId(translateFileId))
            throw ManualException("이미 번역된 내용입니다. 결과 ID : $resultId,  자동번역 ID : $translateFileId")

        response.manualResultDTO!!.translateList.add(
            ManualTranslateDTO(-1, findTranslateFile(translateFileId), content)
        )
        //엔티티에 직접 접근하여 필드 수정한게 아니라 DTO로 접근하여서 안된듯
        translateResultRepository.save(response.toEntity())
    }

    @Transactional(readOnly = true)
    suspend fun existManualTranslateByTranslateFileId(translateFileId: Long) : Boolean = manualTranslateRepository.existsByTranslateFileId(translateFileId)


}