package com.example.demo.docs.component

import com.example.demo.docs.exception.DocumentException
import com.example.demo.docs.type.DocumentType
import com.example.demo.file.util.FileUtil
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile

@Component
class DocumentResolver {

    suspend fun resolve(file : MultipartFile) : DocumentType {
        val ext = FileUtil.findExtension(file.originalFilename ?: file.name)
        return when (ext.lowercase()) {
            "hwp" -> DocumentType.HWP
            "docx" -> DocumentType.DOCX
            "pdf" -> DocumentType.PDF
            else -> throw DocumentException("지원하지 않는 파일 형식입니다. $ext")
        }
    }
}