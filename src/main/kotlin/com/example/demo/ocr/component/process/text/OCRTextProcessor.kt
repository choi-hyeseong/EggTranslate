package com.example.demo.ocr.component.process.text

interface OCRTextProcessor {

    fun postHandle(input : String) : String
}