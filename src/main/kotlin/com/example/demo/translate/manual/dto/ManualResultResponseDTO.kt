package com.example.demo.translate.manual.dto

import com.example.demo.translate.manual.entity.ManualResult
import com.example.demo.translate.manual.type.TranslateState

class ManualResultResponseDTO(
    val manualTranslates: MutableList<ManualTranslateResponseDTO>,
    val translatorId : Long?,
    val state: TranslateState
) {

    constructor(manualResult: ManualResult) : this(manualResult
        .manualTranslate
        .map {
            ManualTranslateDTO(it).toResponseDTO()
        }.toMutableList(), manualResult.translator.id , manualResult.status
    )


}