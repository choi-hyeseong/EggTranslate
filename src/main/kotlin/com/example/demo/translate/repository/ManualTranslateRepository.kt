package com.example.demo.translate.repository

import com.example.demo.translate.entity.ManualTranslate
import org.springframework.data.jpa.repository.JpaRepository

interface ManualTranslateRepository : JpaRepository<ManualTranslate, Long> {

    fun existsByTranslateFileId(id : Long) : Boolean
}