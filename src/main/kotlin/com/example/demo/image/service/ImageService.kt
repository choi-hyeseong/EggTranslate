package com.example.demo.image.service

import com.example.demo.file.service.FileService
import com.example.demo.translate.dto.TranslateFileRequestDTO
import com.example.demo.logger
import com.example.demo.ocr.service.OCRService
import com.example.demo.translate.dto.AutoTranslateResponseDTO
import com.example.demo.translate.service.TranslateService
import com.example.demo.user.basic.service.UserService
import kotlinx.coroutines.*
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class ImageService(
    private val ocrService: OCRService,
    private val translateService: TranslateService,
    private val fileService: FileService,
    private val userService: UserService
) {


    private val log = logger()

    suspend fun handleImage(id : Long, lang: String, image: List<MultipartFile>) : AutoTranslateResponseDTO {
        //save image
        val user = userService.getUser(id)
        return coroutineScope {
            //read parallel logic
            val requestList = image.map {
                async {
                    val response = fileService.saveEntity(fileService.saveImage(user, it))
                    TranslateFileRequestDTO(response, "ko", lang, ocrService.readImage(it))
                }
            }.toList()
            val ocrData = requestList.awaitAll().toList()
            // return translated string
            translateService.translate(user, ocrData)
        }

    }

}