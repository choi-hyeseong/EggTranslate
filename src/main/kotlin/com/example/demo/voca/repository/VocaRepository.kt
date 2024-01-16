package com.example.demo.voca.repository

import com.example.demo.voca.entity.Voca
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface VocaRepository : JpaRepository<Voca, Long> {

    fun findByLangAndOrigin(lang : String, origin : String) : Optional<Voca>

    fun findAllByLang(lang : String) : List<Voca>
}