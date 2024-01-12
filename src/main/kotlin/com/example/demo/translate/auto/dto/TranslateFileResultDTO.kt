package com.example.demo.translate.auto.dto

class TranslateFileResultDTO(
    val id : Long?,
    val origin : String,
    val translate : String,
    val from : String,
    val to : String
) {
    constructor(translateFileDTO: TranslateFileDTO) : this(translateFileDTO.id, translateFileDTO.origin, translateFileDTO.translate, translateFileDTO.from, translateFileDTO.to)
}