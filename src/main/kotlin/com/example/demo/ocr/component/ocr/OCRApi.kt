package com.example.demo.ocr.component.ocr

import org.springframework.web.multipart.MultipartFile

interface OCRApi {

    //read image할때 table 가져오는 response 구성하기
    //즉 기존 OCRAPI를 상속받는 TableOCRAPI를 만들고, readTable을 만듬
    //readImage는 readTable.content로 반환.
    suspend fun readImage(file : MultipartFile) : String

    suspend fun readImage(file : List<MultipartFile>) : List<String> {
        // java default implementation
        // 묶음 요청이 될경우 한 api call내에 전부 요청, 아닐경우 하나씩 요청
        return file.map { readImage(it) }.toList()
    }
}