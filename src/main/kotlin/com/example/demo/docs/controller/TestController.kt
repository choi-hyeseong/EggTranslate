package com.example.demo.docs.controller

import com.example.demo.docs.dto.DocumentWriteResponse
import com.example.demo.docs.factory.DocumentFactory
import com.example.demo.docs.type.DocumentType
import com.example.demo.translate.service.TranslateService
import com.example.demo.translate.web.dto.TranslateRequestDTO
import com.example.demo.translate.web.service.WebTranslateService
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.io.ByteArrayInputStream

@RestController
@RequestMapping("/api/test")
class TestController(private val documentFactory: DocumentFactory, private val webTranslateService: WebTranslateService) {

    @PostMapping(name = "", produces = ["application/json;charset=UTF-8"])
    suspend fun notify(@RequestParam file : MultipartFile) : DocumentWriteResponse? {
        val parser = documentFactory.createParser(DocumentType.PDF, ByteArrayInputStream(file.bytes))
        val response = parser.read()
        val translate = webTranslateService.translateContent(TranslateRequestDTO(
            "ko",
            "en",
            response.content
        ))
        return translate.result?.let { parser.write(it, "") }
    }



}