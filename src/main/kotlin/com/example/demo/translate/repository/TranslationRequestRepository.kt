package com.example.demo.translate.repository

import com.example.demo.translate.entity.TranslationRequest
import org.springframework.data.jpa.repository.JpaRepository

interface TranslationRequestRepository : JpaRepository<TranslationRequest, Long> {
}