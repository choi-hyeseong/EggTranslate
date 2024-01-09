package com.example.demo.translate.dto

import com.example.demo.translate.entity.AutoTranslate
import com.example.demo.user.basic.dto.UserDto

class AutoTranslateDTO(
        val id : Long,
        val userDto: UserDto,
        val origin : String,
        val translate : String,
        val from : String,
        val to : String
) {
        constructor(autoTranslate: AutoTranslate) : this(autoTranslate.id,UserDto(autoTranslate.user), autoTranslate.origin, autoTranslate.translate, autoTranslate.fromLang, autoTranslate.toLang)
        fun toEntity() : AutoTranslate = AutoTranslate(id, userDto.toEntity(userDTO.id, userDTO.name, userDTO.password, userDTO.phone, userDTO.email, userDTO.languages, userDTO.userType), origin, translate, from, to)
}