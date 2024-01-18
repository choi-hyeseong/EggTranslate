package com.example.demo.translate.auto.service

import com.example.demo.translate.manual.dto.ManualResultDTO
import com.example.demo.translate.manual.repository.ManualResultRepository
import com.example.demo.translate.manual.repository.ManualTranslateRepository
import com.example.demo.user.basic.service.UserService
import com.example.demo.user.translator.service.TranslatorService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ManualResultService(
    private val translateResultService: TranslateResultService,
    private val manualResultRepository: ManualResultRepository,
    private val manualTranslateRepository: ManualTranslateRepository
) {

    suspend fun deleteManualResultById(id : Long) {
        manualResultRepository.deleteById(id)
    }

    @Transactional(readOnly = true)
    suspend fun manualResultExists(translateResultId: Long): Boolean {
        return translateResultService.findTranslateResult(translateResultId).manualResultDTO != null
    }


    @Transactional(readOnly = true)
    suspend fun existManualTranslateByTranslateFileId(translateFileId: Long): Boolean =
        manualTranslateRepository.existsByTranslateFileId(translateFileId)


}