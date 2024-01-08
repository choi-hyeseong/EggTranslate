package com.example.demo.file.basic.entity

import com.example.demo.file.translated.entity.TranslateFile
import com.example.demo.user.basic.entity.User
import jakarta.persistence.*

@Entity
class File( // 괄호 안에는 기본 생성자들
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id : Long = -1,

        @JoinColumn(name = "user_id")
           var user : User,

        @Column(nullable = false, length = 255)
           var origin_name : String,

        @Column(nullable = false, length = 255)
           var save_name : String,

        @OneToMany(mappedBy = "file")
           var translate_file : List<TranslateFile>
) {
}