package com.example.demo.translate.dto

data class AutoTranslateRequestDTO(val fileId : Long, val from : String, val to : String, val content : String)