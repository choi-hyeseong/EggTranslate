package com.example.demo.file.dto

import com.example.demo.file.dto.AbstractFileDTO
import com.example.demo.file.entity.ConvertFile
import com.example.demo.user.basic.dto.UserDto
import com.example.demo.user.basic.entity.User


class ConvertFileDTO(
    val id: Long?,
    var saveName: String,
    savePath: String,
    val userDto: UserDto?
) : AbstractFileDTO(savePath) {
    constructor(convertFile: ConvertFile) : this(convertFile.id, convertFile.saveName, convertFile.savePath, convertFile.user?.let { UserDto(it) })

    fun toEntity(user : User?) = ConvertFile(id, saveName, savePath, user)
}