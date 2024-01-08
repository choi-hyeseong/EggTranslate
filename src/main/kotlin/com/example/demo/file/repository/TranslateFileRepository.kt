package com.example.demo.file.repository

import com.example.demo.file.entity.TranslateFile
import org.springframework.data.jpa.repository.JpaRepository

interface TranslateFileRepository : JpaRepository<TranslateFile, Long> {
}