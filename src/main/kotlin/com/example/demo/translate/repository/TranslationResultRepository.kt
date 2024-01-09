package com.example.demo.translate.repository

import com.example.demo.translate.entity.TranslationResult
import org.springframework.data.jpa.repository.JpaRepository

interface TranslationResultRepository :JpaRepository<TranslationResult, Long> {
}