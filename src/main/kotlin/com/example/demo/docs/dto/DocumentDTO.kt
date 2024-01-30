package com.example.demo.docs.dto

import com.example.demo.convertOrNull
import com.example.demo.docs.entity.Document
import com.example.demo.docs.type.DocumentType
import com.example.demo.user.basic.dto.UserDto
import com.example.demo.user.basic.entity.User

class DocumentDTO(
    val id: Long?,
    val type: DocumentType,
    val originName: String,
    val savePath: String,
    val userDto: UserDto?
) {
    constructor(document: Document) : this(
        document.id,
        document.type,
        document.originName,
        document.savePath,
        document.user.convertOrNull { UserDto(it) })

    fun toEntity(user: User?): Document = Document(id, type, originName, savePath, user)

}