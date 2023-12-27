package com.example.demo.component.ocr

import org.springframework.web.multipart.MultipartFile

interface OCRApi {

    suspend fun readImage(file : MultipartFile) : String
}