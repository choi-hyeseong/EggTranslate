package com.example.demo.docs.controller

import com.example.demo.docs.factory.DocumentFactory
import com.example.demo.docs.type.DocumentType
import com.example.demo.translate.service.TranslateService
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/test")
class TestController(private val documentFactory: DocumentFactory) {

    @PostMapping(name = "", produces = ["application/json;charset=UTF-8"])
    suspend fun notify(@RequestParam file : MultipartFile) : String {
        return documentFactory.createParser(DocumentType.HWP, file).read().content
    }



}