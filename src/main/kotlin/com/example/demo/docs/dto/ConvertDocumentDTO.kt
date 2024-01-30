package com.example.demo.docs.dto

import com.example.demo.docs.entity.ConvertDocument
import com.example.demo.docs.type.DocumentType
import com.example.demo.user.basic.dto.UserDto
import com.example.demo.user.basic.entity.User

class ConvertDocumentDTO(
    val id : Long?,
    val type : DocumentType,
    val savePath : String,
    val userDto: UserDto?
) {
   constructor(convertDocument: ConvertDocument) : this(convertDocument.id, convertDocument.type, convertDocument.savePath, convertDocument.user?.let { UserDto(it) })

    fun toEntity(user : User?) : ConvertDocument = ConvertDocument(id, type, savePath, user)

}