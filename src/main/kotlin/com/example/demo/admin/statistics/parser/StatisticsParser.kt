package com.example.demo.admin.statistics.parser

import com.example.demo.admin.statistics.dto.StatisticsResponseDTO
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Date

//sealed로 선언해서 factory 활용
sealed interface StatisticsParser {

    @Transactional
    suspend fun parse(start : Date, end : Date) : StatisticsResponseDTO

}

fun Date.toLocalDateTIme() : LocalDateTime = this.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()