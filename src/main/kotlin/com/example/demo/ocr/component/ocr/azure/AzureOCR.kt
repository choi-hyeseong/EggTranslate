package com.example.demo.ocr.component.ocr.azure

import com.example.demo.ocr.component.ocr.OCRApi
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile

@Component
class AzureOCR(private val azureClient: AzureClient) : OCRApi {
    override suspend fun readImage(file: MultipartFile): String {
        return azureClient.requestImage(file)
    }
}