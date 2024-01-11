package com.example.demo.translate.manual.dto

import com.example.demo.translate.manual.entity.ManualResult
import com.example.demo.translate.manual.type.TranslateState

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