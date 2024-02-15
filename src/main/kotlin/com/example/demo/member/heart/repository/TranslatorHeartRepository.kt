package com.example.demo.member.heart.repository

import com.example.demo.member.heart.entity.TranslatorHeart
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface TranslatorHeartRepository : JpaRepository<TranslatorHeart, Long> {

    fun findByUserId(id : Long) : Optional<TranslatorHeart>

    fun findByTranslatorId(id: Long) : Optional<TranslatorHeart>

    fun findAllByTranslatorId(id : Long) : List<TranslatorHeart>

    fun findByTranslatorIdAndUserId(translatorId : Long, userId : Long) : Optional<TranslatorHeart>

    fun existsByTranslatorIdAndUserId(translatorId: Long, userId: Long) : Boolean
}