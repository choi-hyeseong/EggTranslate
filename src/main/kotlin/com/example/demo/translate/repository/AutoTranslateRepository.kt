package com.example.demo.translate.repository

import com.example.demo.translate.entity.AutoTranslate
import org.springframework.data.jpa.repository.JpaRepository

interface AutoTranslateRepository : JpaRepository<AutoTranslate, Long> {
}