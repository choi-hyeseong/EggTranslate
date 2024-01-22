package com.example.demo.file.entity

import com.example.demo.user.basic.entity.User
import jakarta.persistence.*

@Entity
class ConvertFile( // 괄호 안에는 기본 생성자들
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id : Long?,

    @Column(nullable = false, length = 255)
    var originName : String,

    @Column(nullable = false, length = 255)
    var saveName : String,

    var savePath : String,

    )