package com.example.demo.voca.service

import com.example.demo.logger
import com.example.demo.voca.component.VocaLoader
import com.example.demo.voca.dto.VocaDTO
import com.example.demo.voca.dto.VocaResponseDTO
import com.example.demo.voca.entity.Voca
import com.example.demo.voca.exception.VocaException
import com.example.demo.voca.repository.VocaRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.jpa.domain.JpaSort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.io.File

@Service
class VocaService(
    private val vocaRepository: VocaRepository,
    private val vocaLoader: VocaLoader
) {

    val log = logger()

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
        val response = vocaRepository.findByLangAndOriginContains(lang, word)  //Voca
        if(response.isEmpty())
            throw VocaException ("That word doesn't exist.")
        return response.map { VocaResponseDTO(it) }
    }

    @Transactional
    suspend fun findAll(lang : String) : List<VocaResponseDTO> {
        return vocaRepository.findAllByLang(lang).map { VocaResponseDTO(it) }
    }

    @Transactional(readOnly = true)
    suspend fun findAllContainingVoca(lang : String, content : String) : List<VocaResponseDTO> {
        var copyContent = content
        val voca = findAll(lang).sortedByDescending { it.origin.length }
        return voca.filter {
            if (copyContent.contains(it.origin)) {
                copyContent = copyContent.replace(it.origin, it.translate)
                true
            }
            else
                false
        }
    }
}