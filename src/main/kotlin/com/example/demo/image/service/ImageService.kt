package com.example.demo.image.service

import com.example.demo.common.response.Response
import com.example.demo.file.FileService
import com.example.demo.translate.dto.AutoTranslateRequestDTO
import com.example.demo.translate.dto.AutoTranslateResponseDTO
import com.example.demo.logger
import com.example.demo.ocr.service.OCRService
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

    suspend fun handleImage(id : Long, lang: String, image: List<MultipartFile>): Response<List<AutoTranslateResponseDTO>> {
        //save image
        val user = userService.getUser(id)
        fileService.saveAllEntity(fileService.saveImage(user, image))

        return coroutineScope {
            //read parallel logic
            val requestList = image.map {
                async {
                    ocrService.readImage(it)
                }
            }.toList()
            val ocrData = requestList.awaitAll().map { AutoTranslateRequestDTO("ko", lang, it) }.toList()
            // return translated string
            translateService.translate(ocrData)
        }

    }

}