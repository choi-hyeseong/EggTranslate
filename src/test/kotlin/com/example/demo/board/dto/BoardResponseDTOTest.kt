package com.example.demo.board.dto

import com.example.demo.board.entity.Board
import com.example.demo.board.type.BoardVisibility
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class BoardResponseDTOTest {

    @Test
    fun DENY_BOARD_ID_NULL() {
        val board = Board(null, mockk(), "", "", 0, BoardVisibility.VISIBLE, mutableListOf())
        org.junit.jupiter.api.assertThrows<Exception> { BoardResponseDTO(board) }
    }
}