package com.example.demo.component.ocr

import com.example.demo.component.web.azure.AzureClient
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile

@Component
class AzureOCR(private val azureClient: AzureClient) : OCRApi {
    override suspend fun readImage(file: MultipartFile): String {
        return azureClient.requestImage(file)
    }
}