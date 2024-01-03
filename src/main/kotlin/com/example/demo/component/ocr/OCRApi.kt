package com.example.demo.component.ocr

import org.springframework.web.multipart.MultipartFile

interface OCRApi {

    suspend fun readImage(file : MultipartFile) : String

    suspend fun readImage(file : List<MultipartFile>) : List<String> {
        // java default implementation
        // 묶음 요청이 될경우 한 api call내에 전부 요청, 아닐경우 하나씩 요청
        return file.map { readImage(it) }.toList()
    }
}