package com.example.demo.ocr.component.ocr

import com.example.demo.ocr.component.ocr.model.Paragraph
import org.springframework.web.multipart.MultipartFile

interface ParagraphOCRAPI : OCRApi {

    //어처피 read 아니면 readParagraoh를 써야함.
    suspend fun readParagraph(file : MultipartFile) : List<Paragraph>
}