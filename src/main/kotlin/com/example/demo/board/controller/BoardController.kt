package com.example.demo.board.controller

import com.example.demo.board.dto.BoardRequestDTO
import com.example.demo.board.dto.BoardResponseDTO
import com.example.demo.board.service.BoardService
import com.example.demo.file.service.FileService
import com.example.demo.user.basic.dto.UserDto
import com.example.demo.user.basic.service.UserService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/board")
class BoardController(
    private val userService: UserService,
    private val boardService: BoardService,
    private val fileService: FileService
){

    @GetMapping("/{id}")
    suspend fun getBoard(@PathVariable(required = true, value = "id") id : Long) : BoardResponseDTO {
        val response = boardService.getBoard(id)
        boardService.increaseViewCount(response.id)
        return response
    }
}