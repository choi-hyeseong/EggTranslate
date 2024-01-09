package com.example.demo.translate.dto

import com.example.demo.translate.entity.AutoTranslate
import com.example.demo.user.basic.dto.UserDto

class AutoTranslateDTO(
        val id : Long = -1,
        val userDto: UserDto,
        val translateFile: MutableList<TranslateFileDTO>
) {
        constructor(autoTranslate: AutoTranslate) : this(autoTranslate.id, UserDto(autoTranslate.user), autoTranslate.translateFiles.map { TranslateFileDTO(it) }.toMutableList())
        fun toEntity() : AutoTranslate = AutoTranslate(id, userDto.toEntity(), translateFile.map { it.toEntity() }.toMutableList())

}