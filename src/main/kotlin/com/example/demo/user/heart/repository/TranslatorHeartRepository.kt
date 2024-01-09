package com.example.demo.user.heart.repository

import com.example.demo.user.heart.entity.TranslatorHeart
import com.example.demo.user.translator.entity.Translator
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface TranslatorHeartRepository : JpaRepository<TranslatorHeart, Long> {

    fun findByUserId(id : Long) : Optional<TranslatorHeart>

    fun findByTranslatorId(id: Long) : Optional<TranslatorHeart>

    fun findAllByTranslatorId(id : Long) : List<TranslatorHeart>
}