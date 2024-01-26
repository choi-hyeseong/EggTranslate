package com.example.demo.docs.component

import com.example.demo.docs.dto.DocumentReadResponse
import com.example.demo.docs.dto.DocumentWriteResponse
import org.springframework.web.multipart.MultipartFile

class PDFDocumentParser(file: MultipartFile) : DocumentParser(file) {
    override suspend fun read(): DocumentReadResponse {
        TODO("Not yet implemented")
    }

    override suspend fun write(translate: String, path: String): DocumentWriteResponse {
        TODO("Not yet implemented")
    }
}