package com.example.demo.image.controller

import com.example.demo.common.response.Response
import com.example.demo.image.validation.ImageValid
import com.example.demo.common.valid.lang.LangValid
import com.example.demo.translate.dto.AutoTranslateResponseDTO
import com.example.demo.image.service.ImageService
import com.example.demo.translate.dto.AutoTranslateDTO
import com.example.demo.translate.dto.AutoTranslateResultDTO
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
    suspend fun uploadImage(@RequestParam id : Long, @ImageValid @RequestParam(name = "image", required = true) image : List<MultipartFile>, @LangValid @RequestParam(name = "lang", required = true) lang : String) : Response<AutoTranslateResultDTO> {
        //withContext로 dispatcher 지정해도 안전.
        return Response.ofSuccess(null, imageService.handleImage(id, lang, image))
  }

}