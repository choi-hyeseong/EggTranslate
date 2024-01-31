package com.example.demo.board.dto

import com.example.demo.board.entity.Board
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class BoardResponseDTOTest {

    @Test
    fun DENY_BOARD_ID_NULL() {
        val board = Board(null, mockk(), "", "", 0, mutableListOf())
        org.junit.jupiter.api.assertThrows<Exception> { BoardResponseDTO(board) }
    }
}