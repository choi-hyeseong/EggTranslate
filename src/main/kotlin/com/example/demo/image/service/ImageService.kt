package com.example.demo.image.service

import com.example.demo.file.service.FileService
import com.example.demo.image.dto.ImageDTO
import com.example.demo.translate.auto.dto.TranslateFileRequestDTO
import com.example.demo.logger
import com.example.demo.ocr.service.OCRService
import com.example.demo.translate.auto.dto.AutoTranslateResponseDTO
import com.example.demo.translate.auto.dto.TranslateResultResponseDTO
import com.example.demo.translate.service.TranslateService
import com.example.demo.user.basic.dto.UserDto
import com.example.demo.user.basic.service.UserService
import com.example.demo.user.parent.child.dto.ChildDTO
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

    suspend fun handleImage(imageDTO: ImageDTO): TranslateResultResponseDTO {
        //save image
        val user = userService.getUser(imageDTO.userId)
        //read parallel logic
        val mapRequest = mapToFileDTO(imageDTO.lang, user, imageDTO.file)
        // return translated string
        val response = translateService.requestWebTranslate(mapRequest)
        return translateService.translate(user, null, response)
    }


    private suspend fun mapToFileDTO(lang : String, userDto: UserDto, image: List<MultipartFile>): List<TranslateFileRequestDTO> {
        return coroutineScope {
            val requestList = image.map {
                async {
                    val response = fileService.saveEntity(fileService.saveImage(userDto, it))
                    TranslateFileRequestDTO(response, "ko", lang, ocrService.readImage(it))
                }
            }.toList()
            requestList.awaitAll().toList()
        }
    }

}