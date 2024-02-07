package com.example.demo.user.translator.dto

import com.example.demo.user.basic.dto.UserInfoDTO
import com.example.demo.user.heart.dto.TranslatorHeartResponseDTO
import com.example.demo.user.translator.entity.Translator
import com.example.demo.user.translator.type.TranslatorCategory
import com.example.demo.user.translator.type.TranslatorLevel
import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(value = JsonInclude.Include.NON_NULL)
class TranslatorResponseDTO(
    val id: Long?,
    val career: Int,
    val user : Long?,
    val level: TranslatorLevel,
    val hearts: List<TranslatorHeartResponseDTO>?,
    val certificates: List<String>,
    val categories: List<TranslatorCategory>
)
