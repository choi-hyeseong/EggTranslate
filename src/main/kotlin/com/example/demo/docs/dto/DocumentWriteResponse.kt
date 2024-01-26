package com.example.demo.docs.dto

data class DocumentWriteResponse (
    val success : Boolean,
    val savePath : String,
    val content : String,
    val translate : String
)