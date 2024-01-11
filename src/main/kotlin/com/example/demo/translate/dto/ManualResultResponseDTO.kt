package com.example.demo.translate.dto

import com.example.demo.translate.entity.ManualResult
import com.example.demo.translate.type.TranslateState
import com.example.demo.user.translator.dto.TranslatorDTO

class ManualResultResponseDTO(
    val id: Long = -1,
    val manualTranslates: MutableList<ManualTranslateResponseDTO>,
    val translatorId : Long,
    val state: TranslateState
) {

    constructor(manualResult: ManualResult) : this(manualResult.id, manualResult
        .manualTranslate
        .map {
            ManualTranslateDTO(it).toResponseDTO()
        }.toMutableList(), manualResult.translator.id , manualResult.status
    )


}