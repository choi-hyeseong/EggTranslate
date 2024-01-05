package com.example.demo.user.translator.repository

import com.example.demo.user.translator.entity.Translator
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface TranslatorRepository : JpaRepository<Translator, Long> {
    //CoroutineRepository 사용 위해선 싹다 Reactive로 바꿔야함

    fun findByUserId(userId : String) : Translator?
}