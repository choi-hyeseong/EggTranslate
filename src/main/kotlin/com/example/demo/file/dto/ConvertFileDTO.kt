package com.example.demo.file.dto

import com.example.demo.convertOrNull
import com.example.demo.file.entity.ConvertFile
import com.example.demo.member.user.dto.UserDto
import com.example.demo.member.user.entity.User


class ConvertFileDTO(
    val id: Long?,
    var saveName: String,
    savePath: String,
    val userDto: UserDto?
) : AbstractFileDTO(savePath) {
    constructor(convertFile: ConvertFile) : this(
        convertFile.id,
        convertFile.saveName,
        convertFile.savePath,
        convertFile.user.convertOrNull { UserDto(it) })

    fun toEntity(user: User?) = ConvertFile(id, saveName, savePath, user)

    fun toResponseDTO() : FileSimpleDTO = FileSimpleDTO(id, saveName)
}