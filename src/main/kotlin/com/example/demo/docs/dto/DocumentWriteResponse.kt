package com.example.demo.docs.dto

data class DocumentWriteResponse (
    val savePath : String,
    val content : String,
    val translate : String
)