package com.example.demo.board.service

import com.example.demo.board.dto.BoardEditDTO
import com.example.demo.board.dto.BoardListItemDTO
import com.example.demo.board.dto.BoardRequestDTO
import com.example.demo.board.dto.BoardResponseDTO
import com.example.demo.board.entity.Board
import com.example.demo.board.exception.BoardException
import com.example.demo.common.page.Pageable
import com.example.demo.board.repository.BoardRepository
import com.example.demo.convertOrNull
import com.example.demo.file.dto.FileDTO
import com.example.demo.file.entity.File
import com.example.demo.file.service.FileService
import com.example.demo.user.basic.dto.UserDto
import com.example.demo.user.basic.service.UserService
import jakarta.transaction.Transactional
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class BoardService(
    private val boardRepository: BoardRepository,
    private val userService: UserService,
    private val fileService: FileService
) {

    @Transactional
    suspend fun writeBoard(userId: Long, file: List<FileDTO>, boardRequestDTO: BoardRequestDTO): BoardResponseDTO {
        val user = userService.getUserEntity(userId)
        val saveFiles = fileService.saveAllEntity(file)
            .map { fileService.findFileEntityById(it!!) }.toMutableList()
        val response = boardRepository.save(boardRequestDTO.toEntity(user, saveFiles))
        return BoardResponseDTO(response)
    }

    @Transactional
    suspend fun deleteBoard(id: Long) : BoardResponseDTO {
        val board = getBoard(id)
        boardRepository.deleteById(id)
        return board
    }


    @Transactional
    suspend fun deleteAllBoardByUserId(userId: Long): Int {
        val boards = findAllBoardEntityByUserId(userId)
        //deleteAll보다 빠름.
        boardRepository.deleteAllInBatch(boards)
        return boards.size
    }

    @Transactional
    suspend fun getBoard(id: Long): BoardResponseDTO {
        return BoardResponseDTO(findBoardEntityById(id))
    }


    @Transactional
    suspend fun editBoard(boardEditDTO: BoardEditDTO): BoardResponseDTO {
        val board = findBoardEntityById(boardEditDTO.id)

        if (boardEditDTO.title != null)
            board.title = boardEditDTO.title

        if (boardEditDTO.content != null)
            board.content = boardEditDTO.content

        if (boardEditDTO.visibility != null)
            board.visibility = boardEditDTO.visibility

        if (boardEditDTO.files != null)
            board.files = boardEditDTO.files.map { fileService.findFileEntityById(it) }.toMutableList()

        boardRepository.save(board)
        return getBoard(boardEditDTO.id)
    }

    @Transactional
    suspend fun getBoardList(page: Int, amount: Int): Pageable<BoardListItemDTO> {
        val pageBoard = boardRepository.findAll(PageRequest.of(page, amount))
        val boardListItems = pageBoard.map {
            val content = if (it.content.length > 30) it.content.substring(0, 30) else it.content //내용이 길면 30자까지 자르고 출력
            BoardListItemDTO(it.id!!, it.title, content, it.visibility)
        }.toMutableList()
        return Pageable(page, pageBoard.totalPages, boardListItems)
    }

    @Transactional
    suspend fun findBoardEntityById(id: Long): Board {
        return boardRepository.findById(id).orElseThrow { BoardException("존재하지 않는 게시글입니다.") }
    }


    @Transactional
    suspend fun findAllBoardEntityByUserId(userId: Long): List<Board> {
        return boardRepository.findAllByUserId(userId)
    }

    @Transactional
    suspend fun increaseViewCount(id: Long) {
        boardRepository.save(findBoardEntityById(id).apply {
            count += 1
        })
    }

}