package com.example.demo.file.dto

import com.example.demo.file.entity.File
import com.example.demo.user.basic.dto.UserDto

class FileDTO(
        val fileId : Long = -1,
        val originName : String,
        val saveName : String,
        val user : UserDto,
        val savePath : String

) {
        constructor(file: File) : this(file.id, file.originName, file.saveName, UserDto(file.user), file.savePath)
        fun toEntity() : File = File(
                id = fileId,
                user = user.toEntity(),
                originName = originName,
                saveName = saveName,
                savePath = savePath
        )


}