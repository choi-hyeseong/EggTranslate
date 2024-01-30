package com.example.demo.translate.web.dto

data class TranslateResponseDTO(val isSuccess : Boolean, val from : String, val to : String, val origin : String?, val result : String?) {

    companion object {
        fun empty() : TranslateResponseDTO {
            return TranslateResponseDTO(false, "", "", "", "")
        }
    }
}