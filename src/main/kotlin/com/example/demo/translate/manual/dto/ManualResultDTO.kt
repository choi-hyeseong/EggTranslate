package com.example.demo.translate.manual.dto

import com.example.demo.translate.manual.entity.ManualResult
import com.example.demo.translate.manual.entity.ManualTranslate
import com.example.demo.translate.manual.type.TranslateState
import com.example.demo.user.translator.dto.TranslatorDTO
import com.example.demo.user.translator.entity.Translator

class ManualResultDTO(
    var id: Long?,
    //val translateResult: TranslateResult, // DTO로 하면 순환 참조 문제가 발생함.. 어떻게 해야하지
    val translatorDTO: TranslatorDTO?,
    val status: TranslateState,
    val translateList: MutableList<ManualTranslateDTO>
) {

    constructor(manualResult: ManualResult) : this(
        manualResult.id,
        if (manualResult.translator != null) TranslatorDTO(manualResult.translator!!) else null,
        manualResult.status,
        manualResult.manualTranslate.map {
            ManualTranslateDTO(it)
        }.toMutableList()
    )

    fun toEntity(translator: Translator, translateList: MutableList<ManualTranslate>): ManualResult =
        ManualResult(id, status, translator, translateList)

    fun toResponseDTO() : ManualResultResponseDTO = ManualResultResponseDTO(
        translateList.map { it.toResponseDTO() }.toMutableList(), translatorDTO?.id, status
    )
}
