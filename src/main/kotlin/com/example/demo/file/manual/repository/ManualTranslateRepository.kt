package com.example.demo.file.manual.repository

import com.example.demo.file.manual.entity.ManualTranslate
import org.springframework.data.jpa.repository.JpaRepository

interface ManualTranslateRepository :JpaRepository<ManualTranslate, Long> {
}