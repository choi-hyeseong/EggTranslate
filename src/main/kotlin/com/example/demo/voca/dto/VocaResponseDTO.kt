package com.example.demo.voca.dto

import com.example.demo.voca.entity.Voca
import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class VocaResponseDTO @JsonCreator constructor (

    @JsonProperty(value = "lang")
    val lang : String,
    @JsonProperty(value = "origin")
    val origin : String,
    @JsonProperty(value = "translate")
    val translate : String
) {
    constructor(voca: Voca) : this(voca.lang, voca.origin, voca.translate)

}