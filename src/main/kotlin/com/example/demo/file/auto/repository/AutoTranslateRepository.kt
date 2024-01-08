package com.example.demo.file.auto.repository

import com.example.demo.file.auto.entity.AutoTranslate
import org.springframework.data.jpa.repository.JpaRepository

interface AutoTranslateRepository : JpaRepository<AutoTranslate, Long> {
}