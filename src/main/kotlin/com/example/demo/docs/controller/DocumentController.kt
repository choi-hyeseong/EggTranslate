package com.example.demo.docs.controller

import com.example.demo.common.response.Response
import com.example.demo.docs.dto.DocumentRequestDTO
import com.example.demo.docs.service.DocumentTranslateService
import com.example.demo.translate.auto.dto.TranslateResultResponseDTO
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/document")
class DocumentController(private val documentTranslateService: DocumentTranslateService) {

    @PostMapping("/upload")
    suspend fun request(documentRequestDTO: DocumentRequestDTO) : Response<TranslateResultResponseDTO> {
       return Response.ofSuccess(null, documentTranslateService.request(documentRequestDTO))
    }



}