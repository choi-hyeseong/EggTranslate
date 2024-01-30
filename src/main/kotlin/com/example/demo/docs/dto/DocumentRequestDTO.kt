package com.example.demo.docs.dto

import org.springframework.web.multipart.MultipartFile

data class DocumentRequestDTO(
    val userId: Long?,
    val lang: String,
    val childId: Long?,
    val file: List<MultipartFile>
)