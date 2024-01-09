package com.example.demo.translate.dto

data class AutoTranslateResponseDTO(val isSuccess : Boolean, var fileId : Long, val from : String, val target : String, val origin : String?, val result : String?) {

    companion object {
        fun empty() : AutoTranslateResponseDTO {
            return AutoTranslateResponseDTO(false, -1, "", "", "", "")
        }
    }
}