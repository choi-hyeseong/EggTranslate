package com.example.demo.user.translator.service

import com.example.demo.user.basic.exception.UserNotFoundException
import com.example.demo.user.teacher.dto.TeacherDTO
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
                    .orElseThrow { UserNotFoundException(id, "할당되지 않은 유저 id입니다.") }
            )

    @Transactional(readOnly = true)
    fun findTranslatorById(id : Long) : TranslatorDTO =
            TranslatorDTO(translatorRepository
                    .findById(id)
                    .orElseThrow { UserNotFoundException(id, "할당되지 않은 유저 id입니다.") }
            )

    @Transactional
    suspend fun updateProfile(id : Long, translatorDTO: TranslatorDTO) {
        val existingUser = translatorRepository.findById(id).orElseThrow{
            UserNotFoundException(id, "일치하는 사용자가 없습니다")
        }
        existingUser.career = translatorDTO.career
        existingUser.level = translatorDTO.level
        existingUser.certificates = translatorDTO.certificates
        existingUser.categories = translatorDTO.categories

        translatorRepository.save(existingUser)
    }
}