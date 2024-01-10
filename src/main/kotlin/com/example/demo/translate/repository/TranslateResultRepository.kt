package com.example.demo.translate.repository

import com.example.demo.translate.entity.TranslateResult
import org.springframework.data.jpa.repository.JpaRepository

interface TranslateResultRepository : JpaRepository<TranslateResult, Long> {
}