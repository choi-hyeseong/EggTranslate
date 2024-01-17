package com.example.demo.file.dto

import com.example.demo.file.entity.File
import com.example.demo.user.basic.dto.UserDto
import com.example.demo.user.basic.entity.Member

class FileDTO(
        val id : Long?,
        val originName : String,
        val saveName : String,
        val user : UserDto,
        val savePath : String

) {
        constructor(file: File) : this(file.id, file.originName, file.saveName, UserDto(file.member), file.savePath)
        fun toEntity(member : Member) : File = File(
                id = id,
                member = member,
                originName = originName,
                saveName = saveName,
                savePath = savePath
        )


}