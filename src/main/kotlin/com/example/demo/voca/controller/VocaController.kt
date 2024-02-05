package com.example.demo.voca.controller

import com.example.demo.common.response.Response
import com.example.demo.voca.dto.VocaDTO
import com.example.demo.voca.dto.VocaResponseDTO
import com.example.demo.voca.service.VocaService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.enums.ParameterIn
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
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
@Tag(name = "voca", description = "단어장 관리 및 조회 api입니다.")
class VocaController(private val vocaService: VocaService) {

    @PostMapping("/load")
    @Operation(
        summary = "단어장 불러오기", description = "application.properties에 정의된 경로에서 단어장을 불러옵니다.", responses = [
            ApiResponse(
                responseCode = "200",
                description = "성공",
                content = [Content(mediaType = "application/json")],
                useReturnTypeSchema = true //for generic return value
            ),
            ApiResponse(
                responseCode = "403",
                description = "실패",
                content = []
            )])
    suspend fun load(): Response<Nothing> {
        val response = vocaService.load()
        return Response.ofSuccess("단어장이 $response 개 로드되었습니다.", null)
    }

    @Operation(
        summary = "단어장 검색", description = "단어장 목록에서 단어를 검색합니다.", responses = [
            ApiResponse(
                responseCode = "200",
                description = "성공",
                content = [Content(mediaType = "application/json")],
                useReturnTypeSchema = true //for generic return value
            ),
            ApiResponse(
                responseCode = "403",
                description = "실패",
                content = []
            )]
    )
    @GetMapping("/search")
    suspend fun search(
        @Parameter(name = "lang", required = true, `in` = ParameterIn.QUERY, description = "검색할 언어 코드입니다.")
        @RequestParam lang: String,
        @Parameter(name = "word", required = true, `in` = ParameterIn.QUERY, description = "검색할 단어입니다. 2자 이상 지정해야합니다.")
        @Size(min = 2, message = "단어는 2자 이상이어야 합니다.") @RequestParam word: String
    ): Response<List<VocaResponseDTO>> {
        val response = vocaService.findWord(lang, word)
        return Response.ofSuccess(null, response)
    }

    @Operation(
        summary = "단어장 전체검색", description = "단어장 전체를 검색합니다.", responses = [
            ApiResponse(
                responseCode = "200",
                description = "성공",
                content = [Content(mediaType = "application/json")],
                useReturnTypeSchema = true //for generic return value
            ),
            ApiResponse(
                responseCode = "403",
                description = "실패",
                content = []
            )]
    )
    @GetMapping("")
    suspend fun getAll(
        @Parameter(name = "lang", required = true, `in` = ParameterIn.QUERY, description = "검색할 언어 코드입니다.")
        @RequestParam
        lang: String
    ): Response<List<VocaResponseDTO>> {
        val response = vocaService.findAll(lang)
        return Response.ofSuccess(null, response)
    }
}