package com.example.demo.docs.controller

import com.example.demo.common.response.Response
import com.example.demo.docs.dto.DocumentRequestDTO
import com.example.demo.docs.service.DocumentService
import com.example.demo.docs.service.DocumentTranslateService
import com.example.demo.docs.valid.DocumentRequestValid
import com.example.demo.file.util.FileUtil
import com.example.demo.translate.auto.dto.TranslateResultResponseDTO
import org.springframework.core.io.Resource
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/document")
class DocumentController(private val documentTranslateService: DocumentTranslateService,
    private val documentService: DocumentService) {

    @PostMapping("/upload")
    suspend fun request(@DocumentRequestValid documentRequestDTO: DocumentRequestDTO) : Response<TranslateResultResponseDTO> {
       return Response.ofSuccess(null, documentTranslateService.request(documentRequestDTO))
    }

    // TODO 누구나 파일을 업로드 하고, 이를 다운받을 수 있음. 만약에 바이러스 파일 업로드해서 유포하는 사이트로 악용되면?

    @GetMapping("/{id}")
    suspend fun getDocument(@PathVariable(value = "id", required = true) id : Long) : ResponseEntity<Resource> {
        val document = documentService.findDocumentById(id)
        return ResponseEntity(FileUtil.convertFileToResource(document), HttpHeaders().apply {
            add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=${document.originName}")
        }, HttpStatus.OK)
    }

    @GetMapping("/convert/{id}")
    suspend fun getConvertDocument(@PathVariable(value = "id", required = true) id : Long) : ResponseEntity<Resource> {
        val document = documentService.findConvertDocumentById(id)
        val ext = FileUtil.findExtension(document.savePath)
        return ResponseEntity(FileUtil.convertFileToResource(document), HttpHeaders().apply {
            add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=download.$ext")
        }, HttpStatus.OK)
    }






}