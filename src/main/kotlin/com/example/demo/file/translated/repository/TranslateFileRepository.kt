package com.example.demo.file.translated.repository

import com.example.demo.file.translated.entity.TranslateFile
import org.springframework.data.jpa.repository.JpaRepository

interface TranslateFileRepository : JpaRepository<TranslateFile, Long> {
}