package com.example.demo.file.controller

import com.example.demo.common.response.Response
import com.example.demo.file.dto.FileSimpleDTO
import com.example.demo.image.service.ConvertService
import org.springframework.core.io.Resource
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/convert")
class ConvertFileController(
    val convertService: ConvertService
) {

    @GetMapping("/{id}")
    suspend fun getFile(@PathVariable(value = "id") id: Long): ResponseEntity<Resource> {
        return ResponseEntity(convertService.getFile(id), HttpHeaders().apply {
            add("Content-Type", "image/jpeg")
        }, HttpStatus.OK)
    }

    @GetMapping("/info/{id}")
    suspend fun getFileInfo(@PathVariable(value = "id") id: Long): Response<FileSimpleDTO> {
        return Response.ofSuccess(null, convertService.findFileById(id).toResponseDTO())
    }
}