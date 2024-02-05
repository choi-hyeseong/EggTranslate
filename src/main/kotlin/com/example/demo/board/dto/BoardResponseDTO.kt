package com.example.demo.board.dto

import com.example.demo.board.entity.Board
import com.example.demo.board.type.BoardVisibility
import com.example.demo.file.dto.FileSimpleDTO
import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.util.Assert


class BoardResponseDTO(
    @Schema(name = "user", description = "작성한 유저의 id입니다.")
    val user: Long?,
    @Schema(name = "title", description = "글의 제목입니다.")
    val title: String,
    @Schema(name = "content", description = "글의 내용입니다.")
    val content: String,
    @Schema(name = "count", description = "글의 조회수입니다.")
    var count: Int,
    @Schema(name = "visibility", description = "글의 공개 여부입니다.")
    val visibility: BoardVisibility,
    @Schema(name = "files", description = "글에 포함된 파일 정보입니다.")
    val files: List<FileSimpleDTO> = mutableListOf() //file list
) {
    var id: Long = 0L

    constructor(board: Board) : this(board.user.id, board.title, board.content, board.count, board.visibility, board.files.map { FileSimpleDTO(it.id, it.originName) }) {
        Assert.notNull(board.id, "ID값은 NULL이 와선 안됩니다.")
        this.id = board.id!!
    }
}