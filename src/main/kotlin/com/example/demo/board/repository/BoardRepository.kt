package com.example.demo.board.repository

import com.example.demo.board.entity.Board
import org.springframework.data.jpa.repository.JpaRepository

interface BoardRepository : JpaRepository<Board, Long> {

    fun findAllByUserId(userId : Long) : List<Board>
}