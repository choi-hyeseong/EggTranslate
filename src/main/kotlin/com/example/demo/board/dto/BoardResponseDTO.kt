package com.example.demo.board.dto

import com.example.demo.board.entity.Board
import com.example.demo.board.type.BoardVisibility
import com.example.demo.file.dto.FileSimpleDTO
import org.springframework.util.Assert


class BoardResponseDTO(
    val user: Long?,
    val title: String,
    val content: String,
    var count: Int,
    val visibility: BoardVisibility,
    val files: List<FileSimpleDTO> = mutableListOf() //file list
) {
    var id: Long = 0L

    constructor(board: Board) : this(board.user.id, board.title, board.content, board.count, board.visibility, board.files.map { FileSimpleDTO(it.id, it.originName) }) {
        Assert.isNull(id, "ID값은 NULL이 와선 안됩니다.")
        this.id = board.id!!
    }
}