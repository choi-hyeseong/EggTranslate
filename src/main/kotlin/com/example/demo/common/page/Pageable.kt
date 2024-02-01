package com.example.demo.common.page

data class Pageable<T> (
    //현재 페이지
    val current : Int,
    //최대 페이지, 실제 쿼리에 사용되는 인덱스를 나타냈음. 만약 2페이지면 0,1 페이지에 접근가능하고, max는 1로 설정됨.
    val max : Int,
    //내용
    val content : List<T>,
)