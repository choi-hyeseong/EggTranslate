package com.example.demo.board.dto

import com.example.demo.board.type.BoardVisibility

class BoardEditRequestDTO (
    val title : String?,
    val content : String?,
    val visibility: BoardVisibility?,
    val files : List<Long>?
) {
    fun toEditDTO(id : Long) = BoardEditDTO(id, title, content, visibility, files)
}