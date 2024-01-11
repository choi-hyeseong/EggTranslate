package com.example.demo.translate.auto.repository

import com.example.demo.translate.auto.entity.AutoTranslate
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface AutoTranslateRepository : JpaRepository<AutoTranslate, Long> {

    fun findByUserId(id : Long) : Optional<AutoTranslate>
}