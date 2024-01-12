package com.example.demo.translate.auto.service

import com.example.demo.translate.auto.dto.TranslateFileDTO
import com.example.demo.translate.auto.entity.TranslateFile
import com.example.demo.translate.auto.repository.TranslateFileRepository
import com.example.demo.translate.exception.TranslateException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TranslateFileService(private val translateFileRepository: TranslateFileRepository) {

    @Transactional
    suspend fun findTranslateFileEntity(id: Long): TranslateFile {
        return translateFileRepository.findById(id)
            .orElseThrow { TranslateException("번역 데이터가 존재하지 않습니다.") }
    }

    @Transactional(readOnly = true)
    suspend fun findTranslateFile(translateFileId: Long): TranslateFileDTO {
        return TranslateFileDTO(
            findTranslateFileEntity(translateFileId)
        )
    }

    @Transactional(readOnly = true)
    suspend fun findAllTranslateFiles(translateFileId: List<Long>): MutableList<TranslateFileDTO> {
        return translateFileRepository.findAllById(translateFileId).map {
            TranslateFileDTO(it)
        }.toMutableList()
    }

}