package com.example.demo.common.page

import io.swagger.v3.oas.annotations.media.Schema

data class Pageable<T> (
    //현재 페이지
    @Schema(name = "current", description = "현재 페이지입니다. 0부터 시작합니다.")
    val current : Int,
    //최대 페이지, 실제 쿼리에 사용되는 인덱스를 나타냈음. 만약 2페이지면 0,1 페이지에 접근가능하고, max는 1로 설정됨.
    @Schema(name = "max", description = "최대 페이지를 나타냅니다. max 페이지까지 존재합니다. (1일경우 1페이지까지)")
    val max : Int,
    //내용
    @Schema(name = "content", description = "찾은 데이터를 나타냅니다.")
    val content : List<T>,
)