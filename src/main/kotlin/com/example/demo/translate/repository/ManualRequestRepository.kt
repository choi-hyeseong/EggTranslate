package com.example.demo.translate.repository

import com.example.demo.translate.entity.ManualRequest
import org.springframework.data.jpa.repository.JpaRepository

interface ManualRequestRepository : JpaRepository<ManualRequest, Long> {
}