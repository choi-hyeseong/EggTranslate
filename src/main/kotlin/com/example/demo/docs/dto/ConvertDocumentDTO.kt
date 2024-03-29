package com.example.demo.docs.dto

import com.example.demo.convertOrNull
import com.example.demo.docs.entity.ConvertDocument
import com.example.demo.docs.type.DocumentType
import com.example.demo.file.dto.AbstractFileDTO
import com.example.demo.file.util.FileUtil
import com.example.demo.member.user.dto.UserDto
import com.example.demo.member.user.entity.User

class ConvertDocumentDTO(
    val id: Long?,
    val type: DocumentType,
    savePath: String,
    val userDto: UserDto?
) : AbstractFileDTO(savePath) {
    constructor(convertDocument: ConvertDocument) : this(
        convertDocument.id,
        convertDocument.type,
        convertDocument.savePath,
        convertDocument.user.convertOrNull { UserDto(it) })

    fun toEntity(user: User?): ConvertDocument = ConvertDocument(id, type, savePath, user)

    fun toResponseDTO() : DocumentResponseDTO = DocumentResponseDTO(id!!, type, "download.${FileUtil.findExtension(savePath)}")
}