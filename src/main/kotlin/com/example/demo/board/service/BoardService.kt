package com.example.demo.board.service

import com.example.demo.board.dto.BoardRequestDTO
import com.example.demo.board.dto.BoardResponseDTO
import com.example.demo.board.entity.Board
import com.example.demo.board.exception.BoardException
import com.example.demo.board.repository.BoardRepository
import com.example.demo.file.dto.FileDTO
import com.example.demo.file.entity.File
import com.example.demo.file.service.FileService
import com.example.demo.user.basic.dto.UserDto
import com.example.demo.user.basic.service.UserService
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class BoardService (private val boardRepository: BoardRepository, private val userService: UserService, private val fileService: FileService) {

    @Transactional
    suspend fun writeBoard(userId : Long, file : List<FileDTO>, boardRequestDTO: BoardRequestDTO) : BoardResponseDTO {
        val user = userService.getUserEntity(userId)
        val saveFiles = fileService.saveAllEntity(file)
            .map { fileService.findFileEntityById(it!!) }.toMutableList()
        val response = boardRepository.save(boardRequestDTO.toEntity(user, saveFiles))
        return BoardResponseDTO(response)
    }

    @Transactional
    suspend fun getBoard(id : Long) : BoardResponseDTO {
        return BoardResponseDTO(findBoardEntityById(id))
    }

    @Transactional
    suspend fun findBoardEntityById(id : Long) : Board {
        return boardRepository.findById(id).orElseThrow { BoardException("존재하지 않는 게시글입니다.") }
    }

    @Transactional
    suspend fun increaseViewCount(id : Long) {
        boardRepository.save(findBoardEntityById(id).apply {
            count += 1
        })
    }

}