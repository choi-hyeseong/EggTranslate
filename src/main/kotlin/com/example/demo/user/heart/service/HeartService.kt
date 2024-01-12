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

@Service
class HeartService(
    private val userService: UserService,
    private val translatorService: TranslatorService,
    private val heartRepository: TranslatorHeartRepository
) {

    @Transactional(readOnly = true)
    fun getAllHearts(translatorId : Long) : List<TranslatorHeartResponseDTO> {
        return heartRepository.findAllByTranslatorId(translatorId).map {
            TranslatorHeartResponseDTO(it)
        }
    }

    @Transactional(readOnly = true)
    fun existHeart(translatorId: Long, userId: Long) : Boolean {
        return heartRepository.existsByTranslatorIdAndUserId(translatorId, userId)
    }


    //N:M 연관관계라 entity 직접접근이 나움.
    @Transactional
    fun addHeart(translatorId: Long, userId : Long) {
        val user = userService.getUserEntity(userId)
        val translator = translatorService.findTranslatorEntityById(translatorId)
        val findHeart = heartRepository.findByTranslatorIdAndUserId(translatorId, userId)
        val heart = if (findHeart.isPresent) findHeart.get() else heartRepository.save(TranslatorHeart(null, user, translator))
        user.addHeart(heart)
        translator.addHeart(heart)
        heartRepository.save(heart) //필요성 여부 체크
    }

    @Transactional
    fun removeHeart(translatorId: Long, userId: Long) {
        val findHeart = heartRepository.findByTranslatorIdAndUserId(translatorId, userId).orElseThrow { HeartException("존재하지 않는 좋아요 정보입니다.") }
        findHeart.let {
            it.user?.removeHeart(it)
            it.translator?.removeHeart(it)
            it.user = null
            it.translator = null
        }

        heartRepository.delete(findHeart)
    }

}