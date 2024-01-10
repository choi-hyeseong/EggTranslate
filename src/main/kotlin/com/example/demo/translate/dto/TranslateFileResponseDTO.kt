package com.example.demo.translate.dto

data class TranslateFileResponseDTO(
    val isSuccess: Boolean,
    var fileId: Long,
    val from: String,
    val target: String,
    val origin: String?,
    val result: String?
) {


    companion object {
        fun empty(): TranslateFileResponseDTO {
            return TranslateFileResponseDTO(false, -1, "", "", "", "")
        }
    }
}