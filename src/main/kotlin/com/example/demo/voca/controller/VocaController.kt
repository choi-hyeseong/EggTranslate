package com.example.demo.voca.controller

import com.example.demo.common.response.Response
import com.example.demo.voca.dto.VocaDTO
import com.example.demo.voca.dto.VocaResponseDTO
import com.example.demo.voca.service.VocaService
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.Size
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@Validated
@RequestMapping("/api/voca")
class VocaController(private val vocaService: VocaService) {

    @PostMapping("/load")
    suspend fun load() : Response<Nothing> {
        val response = vocaService.load()
        return Response.ofSuccess("단어장이 $response 개 로드되었습니다.", null)
    }

    @GetMapping("/search")
    suspend fun search(@RequestParam lang : String, @Size(min = 2, message = "단어는 2자 이상이어야 합니다.") @RequestParam word : String) : Response<List<VocaResponseDTO>> {
        val response = vocaService.findWord(lang, word)
        return Response.ofSuccess(null, response)
    }

    @GetMapping("")
    suspend fun getAll(@RequestParam lang : String) : Response<List<VocaResponseDTO>> {
        val response = vocaService.findAll(lang)
        return Response.ofSuccess(null, response)
    }
}