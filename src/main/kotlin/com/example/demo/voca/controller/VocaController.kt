package com.example.demo.voca.controller

import com.example.demo.common.response.Response
import com.example.demo.voca.service.VocaService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/voca")
class VocaController(private val vocaService: VocaService) {

    //TODO 글자 깨짐
    @GetMapping("/load")
    suspend fun load() : Response<Nothing> {
        val response = vocaService.load()
        return Response.ofSuccess("단어장이 $response 개 로드되었습니다.", null)
    }

    @GetMapping("/search")
    suspend fun search(@RequestParam lang : String, @RequestParam word : String) : Response<Nothing> {
        val response = vocaService.findWord(lang, word)
        return Response.ofSuccess(response.translate, null)
    }
}