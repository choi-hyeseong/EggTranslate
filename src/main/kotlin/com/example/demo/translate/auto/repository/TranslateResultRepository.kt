package com.example.demo.translate.auto.repository

import com.example.demo.admin.statistics.dto.StatisticQueryResponse
import com.example.demo.admin.statistics.dto.StatisticsQueryResponseDTO
import com.example.demo.translate.auto.entity.TranslateResult
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.time.LocalDateTime

interface TranslateResultRepository : JpaRepository<TranslateResult, Long> {

    fun findAllByUserId(id : Long) : List<TranslateResult>

    /**
     * JPQL 사용시 Response에 있는 getX의 메소드명과 as 명 맞춰줘야됨
     */
    @Query(value = """
        select DATE(created_date) as date, count(*) as count
        from translate_result
        group by date
        having date between :start and :end
    """, nativeQuery = true)
    fun findStatistics(start : LocalDateTime, end : LocalDateTime) : List<StatisticQueryResponse>

    @Query(value = """
        select DATE(created_date) as date, count(*) as count
        from translate_result
        where user_id = :id
        group by date
        having date between :start and :end
    """,  nativeQuery = true)
    fun findStatisticsWithUserId(id : Long, start : LocalDateTime, end : LocalDateTime) : List<StatisticQueryResponse>

    @Query(value = """
        SELECT DATE(t.created_date) AS date, COUNT(DISTINCT t.id) AS count
        FROM translate_result AS t
        INNER JOIN autotranslate AS a ON t.auto_translate_id = a.id 
        INNER JOIN translate_file AS tf ON a.id = tf.autotranslate_id
        WHERE to_lang = :lang
        GROUP BY DATE(t.created_date), to_lang
        HAVING date between :start and :end
    
    """, nativeQuery = true)
    fun findStatisticsWithLang(lang : String, start: LocalDateTime, end: LocalDateTime) : List<StatisticQueryResponse>
}