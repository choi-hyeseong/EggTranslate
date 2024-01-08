package com.example.demo.translate.repository

import com.example.demo.translate.entity.AutoTranslate
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface AutoTranslateRepository : JpaRepository<AutoTranslate, Long> {

    fun findByUserId(id : Long) : Optional<AutoTranslate>
}