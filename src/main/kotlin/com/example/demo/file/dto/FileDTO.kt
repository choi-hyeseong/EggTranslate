package com.example.demo.file.dto

import com.example.demo.file.entity.File
import com.example.demo.user.basic.dto.UserDto
import com.example.demo.user.basic.entity.User

class FileDTO(
        val id : Long?,
        val originName : String,
        val saveName : String,
        val user : UserDto,
        savePath : String

) : AbstractFileDTO(savePath) {
        constructor(file: File) : this(file.id, file.originName, file.saveName, UserDto(file.user), file.savePath)
        fun toEntity(user : User) : File = File(
                id = id,
                user = user,
                originName = originName,
                saveName = saveName,
                savePath = savePath
        )


}