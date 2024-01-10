package com.example.demo.translate.dto

import com.example.demo.translate.entity.ManualResult
import com.example.demo.translate.type.TranslateState

class ManualResultResponseDTO(
    val id: Long = -1,
    val manualTranslates: MutableList<ManualTranslateDTO>,
    val state: TranslateState
) {

    constructor(manualResult: ManualResult) : this(manualResult.id, manualResult
        .manualTranslate
        .map {
            ManualTranslateDTO(it)
        }.toMutableList(), manualResult.status
    )


}