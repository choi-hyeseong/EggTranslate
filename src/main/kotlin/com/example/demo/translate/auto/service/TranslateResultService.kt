package com.example.demo.translate.auto.service

import com.example.demo.admin.statistics.dto.StatisticsQueryResponseDTO
import com.example.demo.admin.statistics.dto.toResponseDTO
import com.example.demo.admin.statistics.parser.toLocalDateTIme
import com.example.demo.docs.service.DocumentService
import com.example.demo.file.service.FileService
import com.example.demo.translate.auto.dto.TranslateResultDTO
import com.example.demo.translate.auto.entity.TranslateResult
import com.example.demo.translate.auto.repository.TranslateResultRepository
import com.example.demo.translate.exception.TranslateException
import com.example.demo.member.user.service.UserService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.Date

@Service
class TranslateResultService(
    val userService: UserService,
    val fileService: FileService,
    val translateResultRepository: TranslateResultRepository,
    val documentService: DocumentService,
) {


    @Transactional
    suspend fun saveTranslateResult(userId: Long?, translateResultDTO: TranslateResultDTO): Long? {
        val user = if (userId == null) null else userService.getUserEntity(userId)
        val translateFiles = translateResultDTO.autoTranslate.translateFile.map {
            val file = fileService.findFileEntityByIdOrNull(it.file?.id)
            val document = documentService.findDocumentEntityByIdOrNull(it.document?.id)
            it.toEntity(file, document, user)
        }.toMutableList()
        val autoTranslate = translateResultDTO.autoTranslate.toEntity(translateFiles)
        return translateResultRepository.save(translateResultDTO.toEntity(user, autoTranslate, null)).id
    }

    @Transactional
    suspend fun findStatistics(start : Date, end : Date) : List<StatisticsQueryResponseDTO> {
        return translateResultRepository.findStatistics(start.toLocalDateTIme(), end.toLocalDateTIme()).map { it.toResponseDTO() }
    }

    @Transactional
    suspend fun findStatistics(id : Long, start : Date, end : Date) : List<StatisticsQueryResponseDTO> {
        return translateResultRepository.findStatisticsWithUserId(id, start.toLocalDateTIme(), end.toLocalDateTIme()).map { it.toResponseDTO() }
    }

    @Transactional
    suspend fun findTranslateResult(id: Long): TranslateResultDTO {
        return TranslateResultDTO(findTranslateResultEntity(id))
    }

    @Transactional
    suspend fun findTranslateResultEntity(id: Long): TranslateResult {
        return translateResultRepository.findById(id).orElseThrow { TranslateException("존재 하지 않는 번역 결과입니다.") }

    }

    @Transactional
    suspend fun findAllTranslateResultByUserId(id: Long): List<TranslateResultDTO> {
        return findAllTranslateResultEntityByUserId(id).map {
            TranslateResultDTO(it)
        }.toMutableList()
    }

    @Transactional
    suspend fun findAllTranslateResultEntityByUserId(id: Long): List<TranslateResult> {
        return translateResultRepository.findAllByUserId(id)
    }

    @Transactional
    suspend fun findAllEntities() : List<TranslateResult> = translateResultRepository.findAll()

    @Transactional
    suspend fun save(translateResult: TranslateResult) : Long? {
        return translateResultRepository.save(translateResult).id
    }


    @Transactional
    suspend fun saveAll(translateResult: List<TranslateResult>) {
        translateResultRepository.saveAll(translateResult)
    }

    @Transactional
    suspend fun deleteAll(translateResult: List<TranslateResult>) {
        translateResultRepository.deleteAll(translateResult)
    }




}