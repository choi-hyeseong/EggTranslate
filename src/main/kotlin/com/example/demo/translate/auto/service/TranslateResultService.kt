package com.example.demo.translate.auto.service

import com.example.demo.file.service.FileService
import com.example.demo.translate.auto.dto.AutoTranslateDTO
import com.example.demo.translate.auto.dto.TranslateResultDTO
import com.example.demo.translate.auto.entity.AutoTranslate
import com.example.demo.translate.auto.entity.TranslateResult
import com.example.demo.translate.auto.repository.AutoTranslateRepository
import com.example.demo.translate.auto.repository.TranslateResultRepository
import com.example.demo.translate.exception.TranslateException
import com.example.demo.translate.manual.dto.ManualResultDTO
import com.example.demo.translate.manual.dto.ManualTranslateRequestDTO
import com.example.demo.translate.manual.entity.ManualTranslate
import com.example.demo.translate.manual.exception.ManualException
import com.example.demo.translate.manual.repository.ManualResultRepository
import com.example.demo.translate.manual.repository.ManualTranslateRepository
import com.example.demo.translate.manual.type.TranslateState
import com.example.demo.user.basic.service.UserService
import com.example.demo.user.translator.service.TranslatorService
import org.springframework.context.annotation.Lazy
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TranslateResultService(
    val userService: UserService,
    val fileService: FileService,
    val translateResultRepository: TranslateResultRepository,
) {


    @Transactional
    suspend fun saveTranslateResult(userId: Long, translateResultDTO: TranslateResultDTO): Long? {
        val user = userService.getUserEntity(userId)
        val translateFiles = translateResultDTO.autoTranslate.translateFile.map {
            val file = fileService.findFileEntityById(it.file.id!!)
            it.toEntity(file)
        }.toMutableList()
        val autoTranslate = translateResultDTO.autoTranslate.toEntity(user, translateFiles)
        return translateResultRepository.save(translateResultDTO.toEntity(user, autoTranslate, null)).id
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