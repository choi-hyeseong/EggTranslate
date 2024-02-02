package com.example.demo.admin.service

import com.example.demo.board.dto.BoardEditRequestDTO
import com.example.demo.board.dto.BoardRequestDTO
import com.example.demo.board.dto.BoardResponseDTO
import com.example.demo.board.service.BoardService
import com.example.demo.file.dto.FileDTO
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AdminBoardService(
    private val boardService: BoardService
) {

    @Transactional
    suspend fun write( id : Long,  fileDto: List<FileDTO>, boardRequestDTO: BoardRequestDTO,) : BoardResponseDTO {
        return  boardService.writeBoard(id, fileDto, boardRequestDTO)
    }

    @Transactional
    suspend fun edit( id : Long,  boardEditDTO: BoardEditRequestDTO) : BoardResponseDTO {
        return  boardService.editBoard(boardEditDTO.toEditDTO(id))
    }

    @Transactional
    suspend fun delete( id : Long) : BoardResponseDTO {
        return  boardService.deleteBoard(id)
    }
}