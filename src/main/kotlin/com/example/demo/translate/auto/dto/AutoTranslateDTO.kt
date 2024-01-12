package com.example.demo.translate.auto.dto

import com.example.demo.translate.auto.entity.AutoTranslate
import com.example.demo.translate.auto.entity.TranslateFile
import com.example.demo.user.basic.dto.UserDto
import com.example.demo.user.basic.entity.User

class AutoTranslateDTO(
        val id : Long = -1,
        val userDto: UserDto,
        val translateFile: MutableList<TranslateFileDTO>
) {
        constructor(autoTranslate: AutoTranslate) : this(autoTranslate.id, UserDto(autoTranslate.user), autoTranslate.translateFiles.map { TranslateFileDTO(it) }.toMutableList())
        fun toEntity(user : User, translateFiles : MutableList<TranslateFile>) : AutoTranslate = AutoTranslate(id, user, translateFiles)

}