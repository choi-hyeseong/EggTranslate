package com.example.demo.translate.auto.repository

import com.example.demo.translate.auto.entity.TranslateResult
import org.springframework.data.jpa.repository.JpaRepository

interface TranslateResultRepository : JpaRepository<TranslateResult, Long> {

    fun findAllByMemberId(id : Long) : List<TranslateResult>
}