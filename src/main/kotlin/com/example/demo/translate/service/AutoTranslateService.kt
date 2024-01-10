package com.example.demo.translate.service

import com.example.demo.translate.dto.AutoTranslateDTO
import com.example.demo.translate.dto.TranslateFileDTO
import com.example.demo.translate.entity.AutoTranslate
import com.example.demo.translate.exception.TranslateException
import com.example.demo.translate.repository.AutoTranslateRepository
import com.example.demo.translate.repository.TranslateFileRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AutoTranslateService(private val translateFileRepository: TranslateFileRepository,
    private val autoTranslateRepository: AutoTranslateRepository) {

    @Transactional
    fun saveTranslate(translateFileDTO: TranslateFileDTO): Long {
        return translateFileRepository.save(translateFileDTO.toEntity()).id
    }

    @Transactional
    suspend fun saveAllTranslate(translateFileDTO: List<TranslateFileDTO>) : List<Long> {
        return translateFileRepository.saveAll(translateFileDTO.map { it.toEntity() }).map { it.id }
    }

    @Transactional(readOnly = true)
    fun findTranslateFile(translateFileId: Long): TranslateFileDTO {
        return TranslateFileDTO(
            translateFileRepository.findById(translateFileId)
                .orElseThrow { TranslateException("번역 데이터가 존재하지 않습니다.") })
    }

    @Transactional(readOnly = true)
    suspend fun findAllTranslateFiles(translateFileId: List<Long>) : MutableList<TranslateFileDTO> {
        return translateFileRepository.findAllById(translateFileId).map {
            TranslateFileDTO(it)
        }.toMutableList()
    }


    @Transactional
    suspend fun saveAutoTranslate(autoTranslateDTO: AutoTranslateDTO) : Long {
        return autoTranslateRepository.save(autoTranslateDTO.toEntity()).id
    }


    @Transactional
    suspend fun findAutoTranslate(id : Long) : AutoTranslateDTO {
        return AutoTranslateDTO(
            autoTranslateRepository.findById(id).orElseThrow { TranslateException("존재 하지 않는 번역 요청입니다.")  }
        )
    }

}