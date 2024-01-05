package com.example.demo.user.teacher.entity

import com.example.demo.user.basic.entity.User
import jakarta.persistence.Column
import jakarta.persistence.Entity

@Entity
class Teacher(
    userId: String,
    password: String,
    name: String,
    phone: String,
    email: String?,
    language: List<String>,

    @Column(nullable = false, unique = false, length = 25)
    var school : String,

    @Column
    var grade : Int,

    @Column(nullable = false, unique = false, length = 20)
    var className : String,

    @Column(nullable = true, unique = false, length = 20)
    var course : String?,

    @Column(nullable = true)
    var address : String?


) : User(userId, password, name, phone, email, language) {

}