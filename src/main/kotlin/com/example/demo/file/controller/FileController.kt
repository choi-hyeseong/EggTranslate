package com.example.demo.file.controller

import com.example.demo.common.response.Response
import com.example.demo.file.dto.FileDTO
import com.example.demo.file.dto.FileRequestDTO
import com.example.demo.file.dto.FileSimpleDTO
import com.example.demo.file.service.FileService
import com.example.demo.file.util.FileUtil
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
class FileController(private val fileService: FileService) {

    @GetMapping("/{id}")
    suspend fun showFile(@PathVariable(value = "id") id: Long): ResponseEntity<Resource> {
        return ResponseEntity(fileService.getFile(id), HttpHeaders().apply {
            add("Content-Type", "image/jpeg")
        }, HttpStatus.OK)
    }

    @GetMapping("/info/{id}")
    suspend fun getFileInfo(@PathVariable(value = "id") id: Long): Response<FileSimpleDTO> {
        return Response.ofSuccess(null, fileService.findFileById(id).toResponseDTO())
    }

    @GetMapping("/download/{id}")
    suspend fun downloadFile(@PathVariable(value = "id") id: Long): ResponseEntity<Resource> {
        val file = fileService.findFileById(id)
        val filename = URLEncoder.encode(file.originName, "UTF-8")
        return ResponseEntity(FileUtil.convertFileToResource(file), HttpHeaders().apply {
            add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=$filename")
        }, HttpStatus.OK)
    }


}