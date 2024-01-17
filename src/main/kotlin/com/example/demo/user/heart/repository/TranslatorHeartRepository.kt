package com.example.demo.user.heart.repository

import com.example.demo.user.heart.entity.TranslatorHeart
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface TranslatorHeartRepository : JpaRepository<TranslatorHeart, Long> {

    fun findByMemberId(id : Long) : Optional<TranslatorHeart>

    fun findByTranslatorId(id: Long) : Optional<TranslatorHeart>

    fun findAllByTranslatorId(id : Long) : List<TranslatorHeart>

    fun findByTranslatorIdAndMemberId(translatorId : Long, userId : Long) : Optional<TranslatorHeart>

    fun existsByTranslatorIdAndMemberId(translatorId: Long, userId: Long) : Boolean
}