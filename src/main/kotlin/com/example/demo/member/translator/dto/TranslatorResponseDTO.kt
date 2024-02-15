package com.example.demo.member.translator.dto

import com.example.demo.member.heart.dto.TranslatorHeartResponseDTO
import com.example.demo.member.translator.type.TranslatorCategory
import com.example.demo.member.translator.type.TranslatorLevel
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
