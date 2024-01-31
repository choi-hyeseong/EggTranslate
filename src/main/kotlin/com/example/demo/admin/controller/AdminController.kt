package com.example.demo.admin.controller

import com.example.demo.board.dto.BoardRequestDTO
import com.example.demo.board.dto.BoardResponseDTO
import com.example.demo.board.service.BoardService
import com.example.demo.file.service.FileService
import com.example.demo.user.basic.service.UserService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/admin")
class AdminController(
    private val userService: UserService,
    private val fileService: FileService,
    private val boardService: BoardService
) {

    /*
    * Board Part
    */
    @PostMapping("/board/write/{id}")
    suspend fun write(@PathVariable id : Long, @ModelAttribute boardRequestDTO: BoardRequestDTO) : BoardResponseDTO {
        val userDto = userService.getUser(id)
        val fileDto = fileService.saveFile(userDto, boardRequestDTO.file)
        return boardService.writeBoard(id, fileDto, boardRequestDTO)
    }

}