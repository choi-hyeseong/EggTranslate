package com.example.demo.user.translator.repository

import com.example.demo.user.basic.entity.Member
import com.example.demo.user.translator.entity.Translator
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface TranslatorRepository : JpaRepository<Translator, Long> {
    //CoroutineRepository 사용 위해선 싹다 Reactive로 바꿔야함

    fun findByMember(member: Member) : Translator?

    fun findByMemberId(id : Long) : Optional<Translator>

    fun existsByMemberId(id : Long) : Boolean
}