package com.example.demo.translate.repository

import com.example.demo.translate.entity.TranslateFile
import org.springframework.data.jpa.repository.JpaRepository

interface TranslateFileRepository : JpaRepository<TranslateFile, Long> {

    fun findAllById(id : Long) : List<TranslateFile>

}