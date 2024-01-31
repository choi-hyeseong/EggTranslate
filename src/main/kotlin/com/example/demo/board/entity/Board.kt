package com.example.demo.board.entity

import com.example.demo.file.entity.File
import com.example.demo.user.basic.entity.User
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany

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

    @JoinColumn(name = "board_id")
    @OneToMany(cascade = [CascadeType.REMOVE])
    val files : MutableList<File> = mutableListOf() //file list

)