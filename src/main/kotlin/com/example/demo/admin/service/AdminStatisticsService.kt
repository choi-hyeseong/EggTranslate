package com.example.demo.admin.service

import com.example.demo.admin.statistics.dto.StatisticsResponseDTO
import com.example.demo.admin.statistics.factory.StatisticsFactory
import com.example.demo.admin.statistics.type.StatType
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.Date

@Service
class AdminStatisticsService(
    private val statisticsFactory: StatisticsFactory
) {

    @Transactional
    suspend fun parseAllStatistics(start : Date, end : Date) : StatisticsResponseDTO {
        return statisticsFactory.findParser(StatType.ALL).parse(start, end)
    }

    @Transactional
    suspend fun parseUserStatistics(userId : Long, start : Date, end : Date) : StatisticsResponseDTO {
        return statisticsFactory.findParser(StatType.USER, userId).parse(start, end)
    }

    @Transactional
    suspend fun parseLangStatistics(lang : String, start: Date, end: Date) : StatisticsResponseDTO {
        return statisticsFactory.findParser(StatType.LANG, lang = lang).parse(start, end)
    }
}