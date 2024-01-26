package com.example.demo.docs.factory

import com.example.demo.docs.component.DocumentParser
import com.example.demo.docs.component.HwpDocumentParser
import com.example.demo.docs.component.PDFDocumentParser
import com.example.demo.docs.component.WordDocumentParser
import com.example.demo.docs.type.DocumentType
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile

@Component
class DocumentFactory {
    fun createParser(type: DocumentType, file: MultipartFile): DocumentParser {
        return when (type) {
            DocumentType.HWP -> HwpDocumentParser(file)
            DocumentType.DOCX -> WordDocumentParser(file)
            DocumentType.PDF -> PDFDocumentParser(file)
        }
    }
}