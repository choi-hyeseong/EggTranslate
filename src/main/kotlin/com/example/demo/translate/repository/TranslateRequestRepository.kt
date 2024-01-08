package com.example.demo.translate.repository

import com.example.demo.translate.entity.TranslateRequest
import org.springframework.data.jpa.repository.JpaRepository

interface TranslateRequestRepository : JpaRepository<TranslateRequest, Long> {
}