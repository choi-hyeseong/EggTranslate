package com.example.demo.translate.dto

@Deprecated("Not Used")
data class GoogleResponseDTO(var from: String?, var target: String?, var origin : String?, var trans: String) {
    fun toResponseDTO() = TranslateResponseDTO(true, from ?: "", target ?: "", origin, trans)
}