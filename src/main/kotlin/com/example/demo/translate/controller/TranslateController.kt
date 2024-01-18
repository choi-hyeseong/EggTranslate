package com.example.demo.translate.controller

import com.example.demo.common.response.Response
import com.example.demo.translate.auto.dto.TranslateResultResponseDTO
import com.example.demo.translate.auto.dto.TranslateResultSimpleDTO
import com.example.demo.translate.auto.service.TranslateManageService
import com.example.demo.translate.auto.service.TranslateResultService
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
    private val translateResultService: TranslateResultService,
    private val translateManageService: TranslateManageService
) {

    // TODO User Request ID CHECK

    @GetMapping("/{id}")
    suspend fun getResult(@PathVariable id: Long) = Response.ofSuccess(null, translateResultService.findTranslateResult(id).toResponseDTO())

    @GetMapping("")
    suspend fun getAllResult(@RequestParam(value = "id") id: Long): Response<List<TranslateResultSimpleDTO>> {
        return Response.ofSuccess(null, translateResultService.findAllTranslateResultByUserId(id).map {
            TranslateResultSimpleDTO(it)
        })
    }

    //id는 필요 없음.
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
        translateManageService.update(resultId, manualTranslateRequestDTO)
    }
}