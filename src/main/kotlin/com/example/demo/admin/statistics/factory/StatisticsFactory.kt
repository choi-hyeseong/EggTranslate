package com.example.demo.admin.statistics.factory

import com.example.demo.admin.statistics.parser.AllStatisticsParser
import com.example.demo.admin.statistics.parser.LangStatisticsParser
import com.example.demo.admin.statistics.parser.StatisticsParser
import com.example.demo.admin.statistics.parser.UserStatisticsParser
import com.example.demo.admin.statistics.type.StatType
import com.example.demo.translate.auto.service.TranslateResultService
import org.springframework.stereotype.Component

@Component
class StatisticsFactory(
    private val allStatisticsParser: AllStatisticsParser,
    private val translateResultService: TranslateResultService
){

    fun findParser(statType: StatType, userId : Long? = null, lang : String? = null) : StatisticsParser {
        return when(statType) {
            StatType.ALL -> allStatisticsParser
            StatType.LANG -> LangStatisticsParser(lang!!, translateResultService)
            StatType.USER -> UserStatisticsParser(userId!!, translateResultService)
        }
    }
}