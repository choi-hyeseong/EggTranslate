package com.example.demo.translate.controller

import com.example.demo.common.response.Response
import com.example.demo.translate.dto.ManualTranslateDTO
import com.example.demo.translate.dto.TranslateFileDTO
import com.example.demo.translate.dto.TranslateResultResponseDTO
import com.example.demo.translate.service.TranslateDataService
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
    private val translateDataService: TranslateDataService
) {

    @GetMapping("/{id}")
    fun getResult(@PathVariable id: Int) {

    }

    @GetMapping("")
    suspend fun getAllResult(@RequestParam(value = "id") id: Long): Response<List<TranslateResultResponseDTO>> {
        return Response.ofSuccess(null, translateDataService.findAllTranslateResultByUserId(id).map {
            it.toResponseDTO()
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
                userService.getUser(id),
                translatorService.findTranslatorById(translator),
                resultId
            )
        )
    }


    @PutMapping("/{id}")
    suspend fun updateRequest(@PathVariable id: Long, @RequestParam fileId : Long, @RequestParam content : String) {
        translateDataService.update(id, fileId, content)
    }
}