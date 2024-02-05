package com.example.demo.docs.dto

import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.web.multipart.MultipartFile

data class DocumentRequestDTO(
    @Schema(name = "userId", description = "요청할 유저 id입니다. 미 지정시 Guest로 설정됩니다.", required = false)
    val userId: Long?,
    @Schema(name = "lang", description = "번역할 언어의 코드입니다.", required = true)
    val lang: String,
    @Schema(name = "childId", description = "번역을 요청한 자식의 id입니다. 미 지정 가능", required = false)
    val childId: Long?,
    @Schema(name = "file", description = "업로드 하는 파일입니다.")
    val file: List<MultipartFile>
)