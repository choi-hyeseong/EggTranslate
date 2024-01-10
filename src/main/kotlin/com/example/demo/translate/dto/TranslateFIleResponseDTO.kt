package com.example.demo.translate.dto

data class TranslateFIleResponseDTO(
    val isSuccess: Boolean,
    var fileId: Long,
    val from: String,
    val target: String,
    val origin: String?,
    val result: String?
) {


    companion object {
        fun empty(): TranslateFIleResponseDTO {
            return TranslateFIleResponseDTO(false, -1, "", "", "", "")
        }
    }
}