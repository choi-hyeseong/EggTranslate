package com.example.demo.board.dto

import com.example.demo.board.entity.Board
import com.example.demo.file.entity.File
import com.example.demo.user.basic.entity.User
import org.springframework.web.multipart.MultipartFile

class BoardRequestDTO(
    val title: String,
    val content: String,
    val file: MutableList<MultipartFile> = mutableListOf()
) {

    fun toEntity(user: User, file: MutableList<File>): Board = Board(null, user, title, content, 0, file)
}