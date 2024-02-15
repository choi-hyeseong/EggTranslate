package com.example.demo.member.translator.repository

import com.example.demo.member.user.entity.User
import com.example.demo.member.translator.entity.Translator
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface TranslatorRepository : JpaRepository<Translator, Long> {
    //CoroutineRepository 사용 위해선 싹다 Reactive로 바꿔야함

    fun findByUser(user: User) : Translator?

    fun findByUserId(id : Long) : Optional<Translator>

    fun existsByUserId(id : Long) : Boolean
}