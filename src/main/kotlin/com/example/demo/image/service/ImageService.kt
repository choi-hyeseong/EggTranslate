package com.example.demo.image.service

import com.example.demo.file.service.FileService
import com.example.demo.image.dto.ImageDTO
import com.example.demo.translate.auto.dto.TranslateFileRequestDTO
import com.example.demo.logger
import com.example.demo.ocr.component.posthandle.OCRPostHandler
import com.example.demo.translate.web.prehandle.handler.VocaTextReplacer
import com.example.demo.ocr.service.OCRService
import com.example.demo.translate.auto.dto.TranslateResultResponseDTO
import com.example.demo.translate.service.TranslateService
import com.example.demo.user.basic.dto.UserDto
import com.example.demo.user.basic.service.UserService
import com.example.demo.user.parent.service.ParentService
import kotlinx.coroutines.*
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class ImageService(
    private val ocrService: OCRService,
    private val translateService: TranslateService,
    private val fileService: FileService,
    private val userService: UserService,
    private val parentService: ParentService,
    private val ocrPostHandler: OCRPostHandler
) {


    private val log = logger()

    suspend fun handleImage(imageDTO: ImageDTO): TranslateResultResponseDTO {
        //save image
        val user = userService.getUser(imageDTO.userId)
        //read parallel logic
        val mapRequest = mapToFileDTO(imageDTO.lang, user, imageDTO.file)
        // return translated string
        val response = translateService.requestWebTranslate(mapRequest)
        val childDTO =
            if (imageDTO.childId != null)
                parentService.findByChildIdOrNull(imageDTO.userId, imageDTO.childId)
            else null
        return translateService.translate(user, childDTO, response)
    }


    private suspend fun mapToFileDTO(
        lang: String,
        userDto: UserDto,
        image: List<MultipartFile>
    ): List<TranslateFileRequestDTO> {
        return coroutineScope {
            val requestList = image.map {
                async {
                    val response = fileService.saveEntity(fileService.saveImage(userDto, it))
                    val ocrResponse = ocrService.readImage(it)
                    val postHandleResponse = ocrPostHandler.handleText(ocrResponse)
                    TranslateFileRequestDTO(response, "ko", lang, postHandleResponse)
                }
            }.toList()
            requestList.awaitAll().toList()
        }
    }

}