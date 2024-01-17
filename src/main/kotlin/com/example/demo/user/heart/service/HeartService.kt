package com.example.demo.user.heart.service

import com.example.demo.translate.service.TranslateService
import com.example.demo.user.basic.dto.UserDto
import com.example.demo.user.basic.exception.UserNotFoundException
import com.example.demo.user.basic.repository.UserRepository
import com.example.demo.user.basic.service.UserService
import com.example.demo.user.heart.dto.TranslatorHeartResponseDTO
import com.example.demo.user.heart.entity.TranslatorHeart
import com.example.demo.user.heart.exception.HeartException
import com.example.demo.user.heart.repository.TranslatorHeartRepository
import com.example.demo.user.translator.dto.TranslatorDTO
import com.example.demo.user.translator.repository.TranslatorRepository
import com.example.demo.user.translator.service.TranslatorService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import kotlin.jvm.optionals.getOrNull

@Service
class HeartService(
    private val userService: UserService,
    private val translatorService: TranslatorService,
    private val heartRepository: TranslatorHeartRepository
) {

    @Transactional(readOnly = true)
    suspend fun getAllHearts(translatorId : Long) : List<TranslatorHeartResponseDTO> {
        return heartRepository.findAllByTranslatorId(translatorId).map {
            TranslatorHeartResponseDTO(it)
        }
    }

    @Transactional(readOnly = true)
    suspend fun existHeart(translatorId: Long, userId: Long) : Boolean {
        return heartRepository.existsByTranslatorIdAndUserId(translatorId, userId)
    }


    //N:M 연관관계라 entity 직접접근이 나움.
    @Transactional
    suspend fun addHeart(translatorId: Long, userId : Long) {
        val user = userService.getUserEntity(userId)
        val translator = translatorService.findTranslatorEntityById(translatorId)
        val findHeart = heartRepository.findByTranslatorIdAndUserId(translatorId, userId)
        val heart = if (findHeart.isPresent) findHeart.get() else heartRepository.save(TranslatorHeart(null, user, translator))
        user.addHeart(heart)
        translator.addHeart(heart)
        heartRepository.save(heart) //필요성 여부 체크
    }

    @Transactional
    suspend fun removeUserHeart(userId: Long) {
        val findHeart = heartRepository.findByUserId(userId).getOrNull()
        deleteHeart(findHeart)
    }

    @Transactional
    suspend fun removeTranslatorHeart(translatorId: Long) {
        val findHeart = heartRepository.findByTranslatorId(translatorId).getOrNull()
        deleteHeart(findHeart)
    }

    @Transactional
    fun deleteHeart(heart: TranslatorHeart?) {
        if (heart != null) {
            heart.user?.removeHeart(heart)
            heart.translator?.removeHeart(heart)
            heart.user = null
            heart.translator = null
            heartRepository.delete(heart)
        }

    }

}