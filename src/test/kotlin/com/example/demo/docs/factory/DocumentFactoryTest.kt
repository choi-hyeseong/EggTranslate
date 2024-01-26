package com.example.demo.docs.factory

import com.example.demo.docs.component.HwpDocumentParser
import com.example.demo.docs.component.PDFDocumentParser
import com.example.demo.docs.component.WordDocumentParser
import com.example.demo.docs.type.DocumentType
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.web.multipart.MultipartFile


class DocumentFactoryTest {


    @Test
    fun TEST_SATISFY_PARSER() {
        val factory = DocumentFactory()
        val file = mockk<MultipartFile>()
        DocumentType.values().forEach {
            val parser = factory.createParser(it, file)

            val parserClass = when (it) {
                DocumentType.DOCX -> WordDocumentParser::class.java
                DocumentType.HWP -> HwpDocumentParser::class.java
                DocumentType.PDF -> PDFDocumentParser::class.java
            }
            assertEquals(parserClass, parser::class.java)
        }
    }
}