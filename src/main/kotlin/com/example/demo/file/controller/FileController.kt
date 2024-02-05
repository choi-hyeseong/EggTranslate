package com.example.demo.file.controller

import com.example.demo.common.response.Response
import com.example.demo.file.dto.FileDTO
import com.example.demo.file.dto.FileRequestDTO
import com.example.demo.file.dto.FileSimpleDTO
import com.example.demo.file.service.FileService
import com.example.demo.file.util.FileUtil
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.enums.ParameterIn
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.apache.tomcat.jni.FileInfo
import org.springframework.core.io.Resource
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.net.URLEncoder

@RestController
@RequestMapping("/api/file")
@Tag(name = "file", description = "파일 조회 api입니다.")
class FileController(private val fileService: FileService) {

    @GetMapping("/{id}")
    @Operation(
        summary = "이미지 불러오기", description = "해당 id를 가진 파일을 이미지의 형태로 불러옵니다.", responses = [
            ApiResponse(
                responseCode = "200",
                description = "성공",
                content = [Content(mediaType = "application/json")]
            ),
            ApiResponse(
                responseCode = "403",
                description = "실패",
                content = []
            )]
    )
    suspend fun showFile(
        @Parameter(name = "id", `in` = ParameterIn.PATH, description = "조회할 파일의 id입니다.", required = true)
        @PathVariable(value = "id") id: Long
    ): ResponseEntity<Resource> {
        return ResponseEntity(fileService.getFile(id), HttpHeaders().apply {
            add("Content-Type", "image/jpeg")
        }, HttpStatus.OK)
    }

    @Operation(
        summary = "파일 정보 불러오기", description = "해당 id를 가진 파일의 정보를 불러옵니다.", responses = [
            ApiResponse(
                responseCode = "200",
                description = "성공",
                content = [Content(mediaType = "application/json")]
            ),
            ApiResponse(
                responseCode = "403",
                description = "실패",
                content = []
            )]
    )
    @GetMapping("/info/{id}")
    suspend fun getFileInfo(
        @Parameter(name = "id", `in` = ParameterIn.PATH, description = "조회할 파일의 id입니다.", required = true)
        @PathVariable(value = "id") id: Long
    ): Response<FileSimpleDTO> {
        return Response.ofSuccess(null, fileService.findFileById(id).toResponseDTO())
    }

    @Operation(
        summary = "파일 다운로드", description = "해당 id를 가진 파일을 다운로드 합니다.", responses = [
            ApiResponse(
                responseCode = "200",
                description = "성공",
                content = [Content(mediaType = "application/json")]
            ),
            ApiResponse(
                responseCode = "403",
                description = "실패",
                content = []
            )]
    )
    @GetMapping("/download/{id}")
    suspend fun downloadFile(
        @Parameter(name = "id", `in` = ParameterIn.PATH, description = "다운로드할 파일의 id입니다.", required = true)
        @PathVariable(value = "id") id: Long
    ): ResponseEntity<Resource> {
        val file = fileService.findFileById(id)
        val filename = URLEncoder.encode(file.originName, "UTF-8")
        return ResponseEntity(FileUtil.convertFileToResource(file), HttpHeaders().apply {
            add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=$filename")
        }, HttpStatus.OK)
    }


}