package com.example.demo.ocr.component.ocr.azure

import com.example.demo.ocr.component.ocr.OCRApi
import com.example.demo.ocr.component.ocr.ParagraphOCRAPI
import com.example.demo.ocr.component.ocr.azure.dto.AzureResponseDTO
import com.example.demo.ocr.component.ocr.model.Paragraph
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile

@Component
class AzureOCR(private val azureClient: AzureClient) : ParagraphOCRAPI {
    override suspend fun readParagraph(file: MultipartFile): List<Paragraph> {
        return readAll(file).paragraphs
    }

    override suspend fun readImage(file: MultipartFile): String {
        return readAll(file).content
    }

    suspend fun readAll(file: MultipartFile) : AzureResponseDTO {
        return azureClient.requestImage(file)
    }
}