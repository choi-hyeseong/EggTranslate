package com.example.demo.translate.manual.dto

import com.fasterxml.jackson.annotation.JsonProperty

//왜 다른 클래스는 잘 되는데 얘는 역직렬화 문제가 발생하냐?
//-> 생성자 내의 변수가 하나이기 때문에
class ManualTranslateRequestDTO(
    @JsonProperty(value = "data")
    val data: List<ManualTranslateData>
)
class ManualTranslateData(val file: Long, val content: String)