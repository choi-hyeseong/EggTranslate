package com.example.demo.translate.auto.service

import com.example.demo.translate.auto.dto.AutoTranslateDTO
import com.example.demo.translate.auto.entity.AutoTranslate
import com.example.demo.translate.auto.repository.AutoTranslateRepository
import com.example.demo.translate.exception.TranslateException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AutoTranslateService(val autoTranslateRepository: AutoTranslateRepository) {

    @Transactional
    fun findAutoTranslateEntityById(id: Long): AutoTranslate {
        return autoTranslateRepository.findById(id)
            .orElseThrow { TranslateException("존재 하지 않는 번역 데이터 리스트 입니다.") }
    }

    @Transactional
    suspend fun findAutoTranslateById(id: Long): AutoTranslateDTO {
        return AutoTranslateDTO(
            findAutoTranslateEntityById(id)
        )
    }
}