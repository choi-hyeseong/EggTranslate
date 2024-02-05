package com.example.demo.image.dto

import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.web.multipart.MultipartFile

class ImageDTO(

    @Schema(description = "유저 id. 미 지정시 Guest로 설정", required = false)
    val userId : Long?,
    @Schema(description = "번역할 언어 코드", required = true)
    val lang : String,
    @Schema(description = "요청한 자식 id. 미지정 가능", required = false)
    val childId: Long?,
    @Schema(description = "번역된 이미지 생성할지 여부", required = false)
    val image : Boolean?,
    @Schema(description = "업로드할 이미지. Multipart file", required = true)
    val file : List<MultipartFile>
)