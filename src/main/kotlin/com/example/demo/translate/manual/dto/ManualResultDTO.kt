package com.example.demo.translate.manual.dto

import com.example.demo.translate.manual.entity.ManualResult
import com.example.demo.translate.manual.type.TranslateState
import com.example.demo.user.translator.dto.TranslatorDTO

class ManualResultDTO(
    var id: Long = -1,
    //val translateResult: TranslateResult, // DTO로 하면 순환 참조 문제가 발생함.. 어떻게 해야하지
    val translatorDTO: TranslatorDTO,
    val status: TranslateState,
    val translateList: MutableList<ManualTranslateDTO>
) {

    constructor(manualResult: ManualResult) : this(
        manualResult.id,
        TranslatorDTO(manualResult.translator),
        manualResult.status,
        manualResult.manualTranslate.map {
            ManualTranslateDTO(it)
        }.toMutableList()
    )

    fun toEntity(): ManualResult =
        ManualResult(id, status, translatorDTO.toEntity(), translateList.map {
            it.toEntity()
        }.toMutableList())
}
