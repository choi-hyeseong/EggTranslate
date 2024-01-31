package com.example.demo.board.dto

import com.example.demo.board.entity.Board
import com.example.demo.file.dto.FileSimpleDTO


class BoardResponseDTO(
    val id: Long?,
    val user: Long?,
    val title: String,
    val content: String,
    var count: Int,
    val files: List<FileSimpleDTO> = mutableListOf() //file list
) {
    constructor(board: Board) : this(board.id, board.user.id, board.title, board.content, board.count, board.files.map { FileSimpleDTO(it.id, it.originName) })
}