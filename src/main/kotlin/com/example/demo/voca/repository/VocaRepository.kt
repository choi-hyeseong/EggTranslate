package com.example.demo.voca.repository

import com.example.demo.voca.entity.Voca
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface VocaRepository : JpaRepository<Voca, Long> {

    fun findByLangAndOriginStartsWith(lang : String, origin : String) : List<Voca>

    fun findAllByLang(lang : String) : List<Voca>
}