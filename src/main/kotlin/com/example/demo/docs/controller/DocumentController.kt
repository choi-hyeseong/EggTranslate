package com.example.demo.docs.controller

import com.example.demo.common.response.Response
import com.example.demo.docs.dto.DocumentDTO
import com.example.demo.docs.dto.DocumentRequestDTO
import com.example.demo.docs.dto.DocumentResponseDTO
import com.example.demo.docs.service.DocumentService
import com.example.demo.docs.service.DocumentTranslateService
import com.example.demo.docs.valid.DocumentRequestValid
import com.example.demo.file.util.FileUtil
import com.example.demo.translate.auto.dto.TranslateResultResponseDTO
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.enums.ParameterIn
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.core.io.Resource
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URLEncoder

@RestController
@RequestMapping("/api/document")
@Tag(name = "document", description = "문서 관리 및 번역 api 입니다.")
class DocumentController(private val documentTranslateService: DocumentTranslateService,
    private val documentService: DocumentService) {

    @Operation(
        summary = "문서 번역하기", description = "업로드한 문서를 번역해 반환합니다.", responses = [
            ApiResponse(
                responseCode = "200",
                description = "성공",
                content = [Content(mediaType = "application/json")],
                useReturnTypeSchema = true
            ),
            ApiResponse(
                responseCode = "403",
                description = "실패",
                content = []
            )]
    )
    @PostMapping("/upload")
    suspend fun request(@DocumentRequestValid documentRequestDTO: DocumentRequestDTO) : Response<TranslateResultResponseDTO> {
       return Response.ofSuccess(null, documentTranslateService.request(documentRequestDTO))
    }

    // TODO 누구나 파일을 업로드 하고, 이를 다운받을 수 있음. 만약에 바이러스 파일 업로드해서 유포하는 사이트로 악용되면?
    // 2024.02.05 -> 추후 로그인 기능 추가시, 본인 / 번역가 아닌이상 다른 사람의 파일을 업로드 및 다운로드 할 수 없음.

    @Operation(
        summary = "문서 다운로드", description = "업로드한 문서를 다운로드합니다.", responses = [
            ApiResponse(
                responseCode = "200",
                description = "성공",
                content = [Content(mediaType = "application/json")],
                useReturnTypeSchema = true
            ),
            ApiResponse(
                responseCode = "403",
                description = "실패",
                content = []
            )]
    )
    @GetMapping("/{id}")
    suspend fun getDocument(
        @Parameter(name = "id", `in` = ParameterIn.PATH, description = "요청할 파일의 id입니다.")
        @PathVariable(value = "id", required = true) id : Long) : ResponseEntity<Resource> {
        val document = documentService.findDocumentById(id)
        val filename = URLEncoder.encode(document.originName, "UTF-8")
        return ResponseEntity(FileUtil.convertFileToResource(document), HttpHeaders().apply {
            add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=$filename")
        }, HttpStatus.OK)
    }


    @Operation(
        summary = "문서 정보확인", description = "업로드한 문서의 정보를 확인합니다.", responses = [
            ApiResponse(
                responseCode = "200",
                description = "성공",
                content = [Content(mediaType = "application/json")],
                useReturnTypeSchema = true
            ),
            ApiResponse(
                responseCode = "403",
                description = "실패",
                content = []
            )]
    )
    @GetMapping("/info/{id}")
    suspend fun getDocumentInfo(
        @Parameter(name = "id", `in` = ParameterIn.PATH, description = "요청할 파일의 id입니다.")
        @PathVariable(value = "id", required = true) id : Long) : Response<DocumentResponseDTO> {
        val document = documentService.findDocumentById(id)
        return Response.ofSuccess(null, document.toResponseDTO())
    }

    @Operation(
        summary = "번역된 문서 다운로드", description = "번역된 문서를 다운로드합니다.", responses = [
            ApiResponse(
                responseCode = "200",
                description = "성공",
                content = [Content(mediaType = "application/json")],
                useReturnTypeSchema = true
            ),
            ApiResponse(
                responseCode = "403",
                description = "실패",
                content = []
            )]
    )
    @GetMapping("/convert/{id}")
    suspend fun getConvertDocument(
        @Parameter(name = "id", `in` = ParameterIn.PATH, description = "요청할 번역된 파일의 id입니다.")
        @PathVariable(value = "id", required = true) id : Long) : ResponseEntity<Resource> {
        val document = documentService.findConvertDocumentById(id)
        val ext = FileUtil.findExtension(document.savePath)
        return ResponseEntity(FileUtil.convertFileToResource(document), HttpHeaders().apply {
            add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=download.$ext")
        }, HttpStatus.OK)
    }


    @Operation(
        summary = "번역된 문서 정보 확인", description = "번역된 문서의 정보를 확인합니다.", responses = [
            ApiResponse(
                responseCode = "200",
                description = "성공",
                content = [Content(mediaType = "application/json")],
                useReturnTypeSchema = true
            ),
            ApiResponse(
                responseCode = "403",
                description = "실패",
                content = []
            )]
    )
    @GetMapping("/convert/info/{id}")
    suspend fun getConvertDocumentInfo(
        @Parameter(name = "id", `in` = ParameterIn.PATH, description = "요청할 파일의 id입니다.")
        @PathVariable(value = "id", required = true) id : Long) : Response<DocumentResponseDTO> {
        val document = documentService.findConvertDocumentById(id)
        return Response.ofSuccess(null, document.toResponseDTO())
    }





}