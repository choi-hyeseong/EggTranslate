package com.example.demo.admin.statistics.dto

import com.example.demo.admin.statistics.type.StatType
import com.fasterxml.jackson.annotation.JsonFormat
import java.util.Date

data class StatisticsResponseDTO (
    @JsonFormat(pattern="yyyy-MM-dd")
    val startDate : Date,
    @JsonFormat(pattern="yyyy-MM-dd")
    val endDate : Date,
    val type : StatType,
    val data : List<StatisticsQueryResponseDTO>,
)