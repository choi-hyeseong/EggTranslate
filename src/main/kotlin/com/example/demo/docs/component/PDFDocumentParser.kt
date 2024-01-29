package com.example.demo.docs.component

import com.example.demo.docs.dto.DocumentReadResponse
import com.example.demo.docs.dto.DocumentWriteResponse
import org.springframework.web.multipart.MultipartFile
import java.io.ByteArrayInputStream

class PDFDocumentParser(stream : ByteArrayInputStream) : DocumentParser(stream) {
    override suspend fun read(): DocumentReadResponse {
        TODO("Not yet implemented")
    }

    override suspend fun write(translate: String, path: String): DocumentWriteResponse {
        TODO("Not yet implemented")
    }
}