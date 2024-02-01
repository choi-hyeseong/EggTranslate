package com.example.demo.board.dto

import com.example.demo.board.type.BoardVisibility

class BoardListItemDTO (
    val id : Long,
    val title : String,
    val content : String,
    val visibility: BoardVisibility
)