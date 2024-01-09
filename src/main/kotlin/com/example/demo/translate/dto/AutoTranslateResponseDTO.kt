package com.example.demo.translate.dto

data class AutoTranslateResponseDTO(val isSuccess : Boolean, val from : String, val target : String, val origin : String?, val result : String?) {

    companion object {
        fun empty() : AutoTranslateResponseDTO {
            return AutoTranslateResponseDTO(false, "", "", "", "")
        }
    }
}