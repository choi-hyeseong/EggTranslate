package com.example.demo.controller

import com.example.demo.dto.translate.TranslateRequestDTO
import com.example.demo.dto.translate.TranslateResponseDTO
import com.example.demo.common.response.MessageResponse
import com.example.demo.common.response.ObjectResponse
import com.example.demo.common.valid.image.ImageValid
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
class ImageController(private val imageService: ImageService) {

    //분리가능
    @PostMapping("/upload")
    suspend fun uploadImage(@ImageValid @RequestParam(name = "image", required = true) image : List<MultipartFile>) : ObjectResponse<List<TranslateResponseDTO>> {
        //withContext로 dispatcher 지정해도 안전.
        return imageService.handleImage(image)
    }

}