package com.example.demo.file.repository

import com.example.demo.file.entity.ConvertFile
import org.springframework.data.jpa.repository.JpaRepository

interface ConvertFileRepository : JpaRepository<ConvertFile, Long>