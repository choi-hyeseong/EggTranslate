package com.example.demo.docs.entity

import com.example.demo.common.database.entity.BaseEntity
import com.example.demo.docs.type.DocumentType
import jakarta.persistence.*

@Entity
class Document (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id : Long?,

    @Enumerated(value = EnumType.STRING)
    val type : DocumentType,

    @Column
    val originName : String,

    @Column
    val savePath : String,
) : BaseEntity()