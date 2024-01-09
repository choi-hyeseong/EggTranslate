package com.example.demo.translate.dto

import com.example.demo.file.dto.TranslateFileDTO
import com.example.demo.file.entity.TranslateFile
import com.example.demo.translate.entity.AutoTranslate
import com.example.demo.user.basic.dto.UserDto

class AutoTranslateDTO(
        val id : Long,
        val userDto: UserDto,
        val origin : String,
        val translate : String,
        val from : String,
        val to : String,
        val translateFile: MutableList<TranslateFileDTO>
) {
        constructor(autoTranslate: AutoTranslate) : this(autoTranslate.id,UserDto(autoTranslate.user), autoTranslate.origin, autoTranslate.translate, autoTranslate.fromLang, autoTranslate.toLang, autoTranslate.translateFiles.map { TranslateFileDTO(it) }.toMutableList())
        fun toEntity() : AutoTranslate = AutoTranslate(id, userDto.toEntity(), origin, translate, from, to, translateFile.map { it.toEntity() }.toMutableList())
}