package com.example.demo.ocr.service

import com.example.demo.ocr.component.ocr.azure.AzureOCR
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class OCRService(private val ocrApi: AzureOCR) {

    suspend fun readImage(image : MultipartFile) : String{
        return ocrApi.readImage(image)
    }
}