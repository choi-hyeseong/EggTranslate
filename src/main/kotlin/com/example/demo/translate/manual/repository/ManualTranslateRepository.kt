package com.example.demo.translate.manual.repository

import com.example.demo.translate.manual.entity.ManualTranslate
import org.springframework.data.jpa.repository.JpaRepository

interface ManualTranslateRepository : JpaRepository<ManualTranslate, Long> {

    fun existsByTranslateFileId(id : Long) : Boolean
}