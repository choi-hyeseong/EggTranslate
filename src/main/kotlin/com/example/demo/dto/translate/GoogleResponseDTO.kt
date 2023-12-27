package com.example.demo.dto.translate

data class GoogleResponseDTO(var from: String?, var target: String?, var origin : String?, var trans: String) {
    fun toResponseDTO() = TranslateResponseDTO(from ?: "", target ?: "", origin, trans)
}