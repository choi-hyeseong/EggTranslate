package com.example.demo.admin.statistics.parser

import com.example.demo.admin.statistics.dto.StatisticsResponseDTO
import com.example.demo.admin.statistics.type.StatType
import com.example.demo.translate.auto.service.TranslateResultService
import java.util.*

class LangStatisticsParser(private val lang: String, private val translateResultService: TranslateResultService) :
    StatisticsParser {
    override suspend fun parse(start: Date, end: Date): StatisticsResponseDTO {
        val response = translateResultService.findStatistics(lang, start, end)
        return StatisticsResponseDTO(start, end, StatType.LANG, response)
    }
}