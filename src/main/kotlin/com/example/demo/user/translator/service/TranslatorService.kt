package com.example.demo.user.translator.service

import com.example.demo.profile.dto.TranslatorEditDTO
import com.example.demo.user.basic.exception.UserNotFoundException
import com.example.demo.user.basic.service.UserService
import com.example.demo.user.translator.dto.TranslatorDTO
import com.example.demo.user.translator.entity.Translator
import com.example.demo.user.translator.repository.TranslatorRepository
import org.springframework.transaction.annotation.Transactional
import org.springframework.stereotype.Service

@Service
class TranslatorService(
    private val userService: UserService,
    private val translatorRepository: TranslatorRepository
) {
    @Transactional
    suspend fun signUp(translatorDTO: TranslatorDTO): Long? {
        val user = userService.getUserEntity(translatorDTO.user.id!!)
        return translatorRepository.save(translatorDTO.toEntity(user)).id
    }

    @Transactional(readOnly = true)
    suspend fun existTranslatorByUserId(id: Long): Boolean = translatorRepository.existsByMemberId(id)


    @Transactional
    suspend fun findTranslatorEntityById(id: Long): Translator =
        translatorRepository.findById(id).orElseThrow { UserNotFoundException(id, "찾을 수 없는 번역가 id입니다.") }

    @Transactional(readOnly = true)
    suspend fun findTranslatorByUserId(id: Long): TranslatorDTO =
        TranslatorDTO(translatorRepository
            .findByMemberId(id)
            .orElseThrow { UserNotFoundException(id, "해당 id로 번역가를 찾을 수 없습니다.") }
        )

    @Transactional(readOnly = true)
    suspend fun findTranslatorById(id: Long): TranslatorDTO =
        TranslatorDTO(
            findTranslatorEntityById(id)
        )

    @Transactional
    suspend fun delete(id: Long) {
        translatorRepository.deleteById(id)
    }

    @Transactional
    suspend fun updateProfile(id: Long, translatorEditDTO: TranslatorEditDTO) {
        val existingUser = translatorRepository.findByMemberId(id).orElseThrow {
            UserNotFoundException(id, "일치하는 사용자가 없습니다")
        }
        existingUser.career = translatorEditDTO.career
        existingUser.level = translatorEditDTO.level
        existingUser.certificates = translatorEditDTO.certificates
        existingUser.categories = translatorEditDTO.categories

        translatorRepository.save(existingUser)
    }
}