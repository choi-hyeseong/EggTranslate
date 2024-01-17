package com.example.demo.translate.auto.dto

import com.example.demo.translate.auto.entity.AutoTranslate
import com.example.demo.translate.auto.entity.TranslateFile
import com.example.demo.user.basic.dto.UserDto
import com.example.demo.user.basic.entity.Member

class AutoTranslateDTO(
        val id : Long?,
        val userDto: UserDto,
        val translateFile: MutableList<TranslateFileDTO>
) {
        constructor(autoTranslate: AutoTranslate) : this(autoTranslate.id, UserDto(autoTranslate.member), autoTranslate.translateFiles.map { TranslateFileDTO(it) }.toMutableList())
        fun toEntity(member : Member, translateFiles : MutableList<TranslateFile>) : AutoTranslate = AutoTranslate(id, member, translateFiles)

        fun toResponseDTO() = AutoTranslateResponseDTO(translateFile.map { it.toResponseDTO() })
}