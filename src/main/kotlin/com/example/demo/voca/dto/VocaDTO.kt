package com.example.demo.voca.dto

import com.example.demo.voca.entity.Voca
import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
@JsonIgnoreProperties(ignoreUnknown = true)
//json creator가 있어야 생성자 인식가능
class VocaDTO @JsonCreator constructor (

    @JsonProperty(value = "lang")
    val lang : String,
    @JsonProperty(value = "origin")
    val origin : String,
    @JsonProperty(value = "translate")
    val translate : String
) {
    //json내에 id값이 있어선 안됨. (auto increase라 지정안해두면 알아서 로드 됨.)
    private var id : Long? = null

    constructor(voca: Voca) : this(voca.lang, voca.origin, voca.translate) {
        this.id = voca.id
    }

    fun toEntity() : Voca = Voca(id, lang, origin, translate) //null로 안해두면 select 발생함. 굳이 엔티티 저장할때는 id값 필요 없음 (리셋됨)
}