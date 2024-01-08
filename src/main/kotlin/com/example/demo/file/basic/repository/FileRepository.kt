package com.example.demo.file.basic.repository

import com.example.demo.file.basic.entity.File
import org.springframework.data.jpa.repository.JpaRepository

interface FileRepository : JpaRepository<File, Long> {
}