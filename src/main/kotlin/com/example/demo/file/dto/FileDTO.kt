package com.example.demo.file.dto

import com.example.demo.convertOrNull
import com.example.demo.file.entity.File
import com.example.demo.user.basic.dto.UserDto
import com.example.demo.user.basic.entity.User

class FileDTO(
    val id: Long?,
    val originName: String,
    val saveName: String,
    val user: UserDto?,
    savePath: String

) : AbstractFileDTO(savePath) {
    constructor(file: File) : this(
        file.id,
        file.originName,
        file.saveName,
        file.user.convertOrNull { UserDto(it) },
        file.savePath
    )

    fun toEntity(user: User?): File = File(
        id = id,
        user = user,
        originName = originName,
        saveName = saveName,
        savePath = savePath
    )

    fun toResponseDTO() : FileSimpleDTO = FileSimpleDTO(id, originName)


}