package com.example.demo.common.page

data class Pageable<T> (
    //현재 페이지
    val current : Int,
    //최대 페이지
    val max : Int,
    //내용
    val content : List<T>,
)