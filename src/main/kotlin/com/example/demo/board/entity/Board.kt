package com.example.demo.board.entity

import com.example.demo.board.type.BoardVisibility
import com.example.demo.file.entity.File
import com.example.demo.member.user.entity.User
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
    var title : String,

    @Column(columnDefinition = "TEXT")
    var content : String,

    @Column
    var count : Int,

    //해당 게시글을 보이게 할지 여부 (삭제는 아님)
    @Column
    @Enumerated(value = EnumType.STRING)
    var visibility: BoardVisibility,

    @JoinColumn(name = "board_id")
    @OneToMany(cascade = [CascadeType.REMOVE])
    var files : MutableList<File> = mutableListOf() //file list

) {
    fun updateBoard(title : String?, content : String?, visibility: BoardVisibility?, files: MutableList<File>?) {
        if (title != null)
            this.title = title

        if (content != null)
            this.content = content

        if (visibility != null)
            this.visibility = visibility

        if (files != null)
            this.files = files
    }
}