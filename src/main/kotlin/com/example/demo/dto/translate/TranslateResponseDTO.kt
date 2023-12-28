package com.example.demo.dto.translate

data class TranslateResponseDTO(val isSuccess : Boolean, val from : String, val target : String, val origin : String?, val result : String?) {

    companion object {
        fun empty() : TranslateResponseDTO {
            return TranslateResponseDTO(false, "", "", "", "")
        }
    }
}