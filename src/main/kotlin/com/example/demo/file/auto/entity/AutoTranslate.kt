package com.example.demo.file.auto.entity

import com.example.demo.file.translated.entity.TranslateFile
import com.example.demo.user.basic.entity.User
import jakarta.persistence.*

@Entity
class AutoTranslate(
        @JoinColumn(name = "user_id")
        var user : User,

        @Column(nullable = false)
        var origin : String,

        @Column(nullable = false)
        var translate : String,

        @Column(nullable = false, length = 3)
        var from : String,

        @Column(nullable = false, length = 3)
        var to : String,

        @OneToMany(mappedBy = "AutoTranslate")
        @Column(nullable = false)
        var translateFiles : List<TranslateFile>
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id : Long = -1
}