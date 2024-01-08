package com.example.demo.file.request.repository

import com.example.demo.file.request.entity.TranslateRequest
import org.springframework.data.jpa.repository.JpaRepository

interface TranslateRequestRepository : JpaRepository<TranslateRequest, Long> {
}