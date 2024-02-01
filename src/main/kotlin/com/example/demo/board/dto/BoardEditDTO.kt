package com.example.demo.board.dto

import com.example.demo.board.type.BoardVisibility

//수정할 데이터만 받는 edit dto
data class BoardEditDTO (
    val id : Long,
    val title : String?,
    val content : String?,
    val visibility: BoardVisibility?,
    val files : List<Long>?
)