package com.example.demo.board.dto

import com.example.demo.board.type.BoardVisibility

class BoardListItemDTO(
    val id: Long,
    val title: String,
    var content: String,
    val visibility: BoardVisibility
) {

    init {
        content = if (content.length > 30) content.substring(0, 30) else content
    }
}