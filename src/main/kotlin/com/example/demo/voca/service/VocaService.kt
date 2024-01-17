package com.example.demo.voca.service

import com.example.demo.logger
import com.example.demo.voca.component.VocaLoader
import com.example.demo.voca.dto.VocaDTO
import com.example.demo.voca.dto.VocaResponseDTO
import com.example.demo.voca.entity.Voca
import com.example.demo.voca.exception.VocaException
import com.example.demo.voca.repository.VocaRepository
import kotlinx.coroutines.runBlocking
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.io.File
import javax.annotation.PostConstruct

@Service
class VocaService(
    private val vocaRepository: VocaRepository,
    private val vocaLoader: VocaLoader
) {

    val log = logger()

    @PostConstruct
    fun postHandle() {
        runBlocking {
            load()
        }
    }

    @Transactional
    suspend fun load() : Int {
        try {
            vocaRepository.deleteAll()
            val vocaList = vocaLoader.load()
            val entityVoca = vocaList.map { it.toEntity() }.toMutableList()
            return vocaRepository.saveAll(entityVoca).size
        }
        catch (e : Exception) {
            throw VocaException(e.message ?: e.localizedMessage) //Voca Exception으로 감쌈
        }
    }

    @Transactional(readOnly = true)
    suspend fun findWord(lang : String, word : String) : List<VocaResponseDTO> {
        val response = vocaRepository.findByLangAndOriginStartsWith(lang, word)  //Voca
        if(response.isEmpty())
            throw VocaException ("That word doesn't exist.")
        return response.map { VocaResponseDTO(it) }
    }

    @Transactional
    suspend fun findAll(lang : String) : List<VocaResponseDTO> {
        return vocaRepository.findAllByLang(lang).map { VocaResponseDTO(it) }
    }
}