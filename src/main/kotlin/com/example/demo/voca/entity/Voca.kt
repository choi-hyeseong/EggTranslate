package com.example.demo.voca.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
class Voca(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id : Long?,

    @Column(length = 4)
    val lang : String,

    @Column(columnDefinition = "TEXT")
    var origin : String,

    @Column(columnDefinition = "TEXT")
    var translate : String,
)