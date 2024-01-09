package com.example.demo.file.request.repository

import com.example.demo.translate.entity.TranslationRequest
import org.springframework.data.jpa.repository.JpaRepository

interface TranslateRequestRepository : JpaRepository<TranslationRequest, Long> {
}