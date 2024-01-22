package com.example.demo.ocr.service

import com.example.demo.ocr.component.ocr.azure.AzureOCR
import com.example.demo.ocr.component.ocr.azure.dto.AzureResponseDTO
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class OCRService(private val ocrApi: AzureOCR) {

    suspend fun readImage(image : MultipartFile) : String{
        return ocrApi.readImage(image)
    }

    suspend fun readAll(image : MultipartFile) : AzureResponseDTO {
        return ocrApi.readAll(image)
    }
}