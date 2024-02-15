package com.example.demo.board.dto

import com.example.demo.board.entity.Board
import com.example.demo.board.type.BoardVisibility
import com.example.demo.file.entity.File
import com.example.demo.member.user.entity.User
import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.web.multipart.MultipartFile

class BoardRequestDTO(
    @Schema(name = "title", description = "작성할 게시글의 제목입니다.")
    val title: String,
    @Schema(name = "content", description = "작성할 게시글의 내용입니다.")
    val content: String,
    @Schema(name = "file", description = "작성할 게시글에 포함될 파일입니다.")
    val file: MutableList<MultipartFile> = mutableListOf()
) {

    fun toEntity(user: User, file: MutableList<File>): Board = Board(null, user, title, content, 0, BoardVisibility.VISIBLE, file)
}