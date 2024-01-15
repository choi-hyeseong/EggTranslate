package com.example.demo.file.controller

import com.example.demo.file.dto.FileDTO
import com.example.demo.file.dto.FileRequestDTO
import com.example.demo.file.service.FileService
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

@RestController
@RequestMapping("/api/file")
class FileController(private val fileService: FileService) {

    @GetMapping("/{id}")
    suspend fun getFile(@PathVariable(value = "id") id: Long): ResponseEntity<Resource> {
        return ResponseEntity(fileService.getFile(id), HttpHeaders().apply {
            add("Content-Type", "image/jpeg")
        }, HttpStatus.OK)
    }


}