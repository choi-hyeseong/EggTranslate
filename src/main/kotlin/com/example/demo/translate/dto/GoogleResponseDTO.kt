package com.example.demo.translate.dto

@Deprecated("Not Used")
data class GoogleResponseDTO(var fileId : Long?, var from: String?, var target: String?, var origin : String?, var trans: String) {
    fun toResponseDTO() = AutoTranslateResponseDTO(true, fileId?:-1, from ?: "", target ?: "", origin, trans)
}