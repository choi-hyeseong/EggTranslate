package com.example.demo.image.dto

import org.springframework.web.multipart.MultipartFile

class ImageDTO(
    val userId : Long,
    val lang : String,
    val file : List<MultipartFile>
)