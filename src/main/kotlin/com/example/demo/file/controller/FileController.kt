package com.example.demo.file.controller

import com.example.demo.file.dto.FileDTO
import com.example.demo.file.dto.FileRequestDTO
import com.example.demo.file.service.FileService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/file")
class FileController(private val fileService: FileService) {

    @PostMapping("")
    suspend fun getFile(@RequestBody requestDTO: FileRequestDTO): FileDTO {
        return fileService.getFile(requestDTO.userId, requestDTO.fileId)
    }


}