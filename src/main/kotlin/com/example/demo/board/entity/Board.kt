package com.example.demo.board.entity

import com.example.demo.board.type.BoardVisibility
import com.example.demo.file.entity.File
import com.example.demo.user.basic.entity.User
import jakarta.persistence.*

@Entity
class Board (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id : Long?,

    @JoinColumn(name = "user_id")
    @ManyToOne
    val user : User,

    @Column(length = 100)
    val title : String,

    @Column(columnDefinition = "TEXT")
    val content : String,

    @Column
    var count : Int,

    //해당 게시글을 보이게 할지 여부 (삭제는 아님)
    @Column
    @Enumerated(value = EnumType.STRING)
    var visibility: BoardVisibility,

    @JoinColumn(name = "board_id")
    @OneToMany(cascade = [CascadeType.REMOVE])
    val files : MutableList<File> = mutableListOf() //file list

)