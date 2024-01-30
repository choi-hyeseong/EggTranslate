package com.example.demo.docs.entity

import com.example.demo.common.database.entity.BaseEntity
import com.example.demo.docs.type.DocumentType
import com.example.demo.user.basic.entity.User
import jakarta.persistence.*

@Entity
class ConvertDocument (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id : Long?,

    @Enumerated(value = EnumType.STRING)
    val type : DocumentType,

    @Column
    val savePath : String,

    @ManyToOne
    @JoinColumn(name = "user_id")
    var user : User?,
) : BaseEntity()