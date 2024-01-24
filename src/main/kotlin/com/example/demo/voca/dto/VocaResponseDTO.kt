package com.example.demo.voca.dto

import com.example.demo.voca.entity.Voca

data class VocaResponseDTO(
    val origin : String,
    val lang : String,
    val translate : String
) {
    constructor(voca: Voca) : this(voca.origin, voca.lang, voca.translate)
}