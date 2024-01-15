package com.example.demo.voca.dto

import com.example.demo.voca.entity.Voca
import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

//json creator가 있어야 생성자 인식가능
class VocaDTO @JsonCreator constructor (
    @JsonProperty(value = "id")
    val id : Long?,
    @JsonProperty(value = "lang")
    val lang : String,
    @JsonProperty(value = "origin")
    val origin : String,
    @JsonProperty(value = "translate")
    val translate : String
) {

    constructor(voca: Voca) : this(voca.id, voca.lang, voca.origin, voca.translate)

    fun toEntity() : Voca = Voca(id, lang, origin, translate)
}