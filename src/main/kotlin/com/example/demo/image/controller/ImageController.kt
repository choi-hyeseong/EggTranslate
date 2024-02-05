package com.example.demo.image.controller

import com.example.demo.common.response.Response
import com.example.demo.image.validation.ImageValid
import com.example.demo.common.valid.lang.LangValid
import com.example.demo.image.dto.ImageDTO
import com.example.demo.image.service.ImageService
import com.example.demo.translate.auto.dto.AutoTranslateResponseDTO
import com.example.demo.translate.auto.dto.TranslateResultResponseDTO
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springdoc.core.annotations.ParameterObject
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/image")
@Tag(name = "image", description = "이미지 번역 api입니다.")
class ImageController(private val imageService: ImageService) {

    //분리가능
    @PostMapping("/upload")
    @Operation(
        summary = "이미지 번역하기", description = "업로드한 이미지를 번역해 반환합니다.", responses = [
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
    suspend fun uploadImage(
        @ImageValid @ModelAttribute image : ImageDTO): Response<TranslateResultResponseDTO> {
        //withContext로 dispatcher 지정해도 안전.
        return Response.ofSuccess(null, imageService.handleImage(image))
    }

}