package com.example.demo.translate.controller

import com.example.demo.common.response.Response
import com.example.demo.translate.auto.dto.TranslateResultResponseDTO
import com.example.demo.translate.auto.dto.TranslateResultSimpleDTO
import com.example.demo.translate.auto.service.AutoTranslateService
import com.example.demo.translate.manual.dto.ManualTranslateDTO
import com.example.demo.translate.manual.dto.ManualTranslateRequestDTO
import com.example.demo.translate.service.TranslateService
import com.example.demo.user.basic.service.UserService
import com.example.demo.user.translator.service.TranslatorService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/translate")
class TranslateController(
    private val userService: UserService,
    private val translatorService: TranslatorService,
    private val translateService: TranslateService,
    private val autoTranslateService: AutoTranslateService
) {

    @GetMapping("/{id}")
    suspend fun getResult(@PathVariable id: Long) = Response.ofSuccess(null, autoTranslateService.findTranslateResult(id).toResponseDTO())

    @GetMapping("")
    suspend fun getAllResult(@RequestParam(value = "id") id: Long): Response<List<TranslateResultSimpleDTO>> {
        return Response.ofSuccess(null, autoTranslateService.findAllTranslateResultByUserId(id).map {
            TranslateResultSimpleDTO(it)
        })
    }


    @PostMapping("/{id}")
    suspend fun createRequest(
        @PathVariable id: Long,
        @RequestParam translator: Long,
        @RequestParam resultId: Long
    ): Response<TranslateResultResponseDTO> {
        return Response.ofSuccess(
            null,
            translateService.request(
                translatorService.findTranslatorById(translator),
                resultId
            )
        )
    }


    @PutMapping("/{id}")
    suspend fun updateRequest(@PathVariable(value = "id") resultId : Long, @RequestBody manualTranslateRequestDTO: ManualTranslateRequestDTO) {
        autoTranslateService.update(resultId, manualTranslateRequestDTO)
    }
}