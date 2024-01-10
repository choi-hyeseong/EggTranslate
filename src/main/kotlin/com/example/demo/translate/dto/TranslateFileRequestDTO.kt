package com.example.demo.translate.dto

data class TranslateFileRequestDTO(val fileId : Long, val from : String, val to : String, val content : String)