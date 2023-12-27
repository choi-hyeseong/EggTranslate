package com.example.demo.controller

import com.example.demo.dto.translate.TranslateRequestDTO
import com.example.demo.dto.translate.TranslateResponseDTO
import com.example.demo.response.MessageResponse
import com.example.demo.response.ObjectResponse
import com.example.demo.service.ImageService
import com.example.demo.service.TranslateService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/image")
class ImageController(private val imageService: ImageService, private val translateService: TranslateService) {

    @PostMapping("/upload")
    suspend fun uploadImage(@RequestParam(name = "image", required = true) image : MultipartFile) : ObjectResponse<TranslateResponseDTO> {
        return imageService.handleImage(image)
    }

    //for test
    @PostMapping("/")
    suspend fun image(@RequestBody translateRequestDTO: TranslateRequestDTO) : ResponseEntity<ObjectResponse<TranslateResponseDTO>> {
        val result = translateService.translate(translateRequestDTO)
        return if (result.isSuccess)
            ResponseEntity(result, HttpStatus.OK)
        else
            ResponseEntity(result, HttpStatus.BAD_REQUEST)
    }

}