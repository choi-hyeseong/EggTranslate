package com.example.demo.translate.dto

import com.example.demo.translate.entity.ManualTranslate

class ManualTranslateDTO(
        val id : Long = -1,
        val translateContent : String,
        val translationRequest : ManualRequestDTO
) {

        constructor(manualTranslate: ManualTranslate) : this(manualTranslate.id, manualTranslate.translateContent, ManualRequestDTO(manualTranslate.manualRequest))
        fun toEntity() : ManualTranslate = ManualTranslate(id, translateContent, translationRequest.toEntity())

}