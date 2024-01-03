package com.example.demo.controller

import com.example.demo.common.response.Response
import com.example.demo.common.valid.image.ImageValid
import com.example.demo.dto.translate.TranslateResponseDTO
import com.example.demo.service.ImageService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/image")
class ImageController(private val imageService: ImageService) {

    //분리가능
    @PostMapping("/upload")
    suspend fun uploadImage(@ImageValid @RequestParam(name = "image", required = true) image : List<MultipartFile>) : Response<List<TranslateResponseDTO>> {
        //withContext로 dispatcher 지정해도 안전.
        return imageService.handleImage(image)
    }

}