package com.example.demo.user.teacher.entity

import com.example.demo.user.basic.entity.User
import jakarta.persistence.*

@Entity
class Teacher(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long?,

    @Column(nullable = false, unique = false, length = 25)
    var school : String,

    @Column
    var grade : Int,

    @Column(nullable = false, unique = false, length = 20)
    var className : String,

    @Column(nullable = true, unique = false, length = 20)
    var course : String?,

    @Column(nullable = true)
    var address : String?,

    @OneToOne
    @JoinColumn(name = "user_id")
    var user: User

) {





}