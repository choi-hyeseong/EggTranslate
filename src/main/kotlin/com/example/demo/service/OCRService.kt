package com.example.demo.service

import com.example.demo.component.ocr.OCRApi
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class OCRService(private val ocrApi: OCRApi) {

    suspend fun readImage(image : MultipartFile) : String{
        return ocrApi.readImage(image)
    }
}