package com.example.demo.docs.dto

import com.example.demo.docs.type.DocumentType

class DocumentResponseDTO (
    val id : Long,
    val type: DocumentType,
    val originName: String
)