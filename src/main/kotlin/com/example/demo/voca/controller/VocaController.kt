package com.example.demo.voca.controller

import com.example.demo.common.response.Response
import com.example.demo.voca.dto.VocaDTO
import com.example.demo.voca.dto.VocaResponseDTO
import com.example.demo.voca.service.VocaService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/voca")
class VocaController(private val vocaService: VocaService) {

    @PostMapping("/load")
    suspend fun load() : Response<Nothing> {
        val response = vocaService.load()
        return Response.ofSuccess("단어장이 $response 개 로드되었습니다.", null)
    }

    @GetMapping("/search")
    suspend fun search(@RequestParam lang : String, @RequestParam word : String) : Response<String> {
        val response = vocaService.findWord(lang, word)
        return Response.ofSuccess(null, response.translate)
    }

    @GetMapping("")
    suspend fun getAll(@RequestParam lang : String) : Response<List<VocaResponseDTO>> {
        val response = vocaService.findAll(lang)
        return Response.ofSuccess(null, response)
    }
}