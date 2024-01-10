package com.example.demo.user.translator.service

import com.example.demo.user.basic.exception.UserNotFoundException
import com.example.demo.user.translator.dto.TranslatorDTO
import com.example.demo.user.translator.repository.TranslatorRepository
import org.springframework.transaction.annotation.Transactional
import org.springframework.stereotype.Service

@Service
class TranslatorService(private val translatorRepository: TranslatorRepository) {
    @Transactional
    fun signUp(translatorDTO: TranslatorDTO) : Long {
        return translatorRepository.save(translatorDTO.toEntity()).id
    }

    @Transactional(readOnly = true)
    fun existTranslatorByUserId(id : Long) : Boolean = translatorRepository.existsByUserId(id)

    @Transactional(readOnly = true)
    fun findTranslatorByUserId(id: Long): TranslatorDTO =
            TranslatorDTO(translatorRepository
                    .findByUserId(id)
                    .orElseThrow { UserNotFoundException(id, "해당 id로 번역가를 찾을 수 없습니다.") }
            )

    @Transactional(readOnly = true)
    fun findTranslatorById(id : Long) : TranslatorDTO =
            TranslatorDTO(translatorRepository
                    .findById(id)
                    .orElseThrow { UserNotFoundException(id, "찾을 수 없는 번역가 id입니다.") }
            )
}