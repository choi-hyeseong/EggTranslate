package com.example.demo.admin.statistics.dto

import com.fasterxml.jackson.annotation.JsonFormat
import java.util.Date

data class StatisticsQueryResponseDTO (
    @JsonFormat(pattern="yyyy-MM-dd")
    val date : Date,
    val count : Long
)

// native query mapping 못하는 문제때문에 interface로 우선 받아옴
interface StatisticQueryResponse {

    fun getDate() : Date
    fun getCount() : Long

}

fun StatisticQueryResponse.toResponseDTO() : StatisticsQueryResponseDTO {
    return StatisticsQueryResponseDTO(getDate(), getCount())
}