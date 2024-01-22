package com.example.demo.image.service

import com.example.demo.file.service.FileService
import com.example.demo.image.dto.ConvertFileDTO
import com.example.demo.image.dto.ImageDTO
import com.example.demo.translate.auto.dto.TranslateFileRequestDTO
import com.example.demo.logger
import com.example.demo.ocr.component.posthandle.OCRPostHandler
import com.example.demo.ocr.service.OCRService
import com.example.demo.translate.auto.dto.TranslateFileResponseDTO
import com.example.demo.translate.auto.dto.TranslateResultResponseDTO
import com.example.demo.translate.service.TranslateService
import com.example.demo.translate.web.dto.TranslateRequestDTO
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
    private val convertService: ConvertService,
    private val ocrPostHandler: OCRPostHandler
) {

    private val log = logger()

    suspend fun handleImage(imageDTO: ImageDTO): TranslateResultResponseDTO {
        //save image
        val user = userService.getUser(imageDTO.userId)
        //read parallel logic
        val response = requestImage(imageDTO.image ?: false, imageDTO.lang, user, imageDTO.file)
        // return translated string
        val childDTO = parentService.findByChildIdOrNull(imageDTO.userId, imageDTO.childId)
        return translateService.saveTranslate(user, childDTO, response)
    }


    private suspend fun requestImage(
        isConvert: Boolean,
        lang: String,
        userDto: UserDto,
        image: List<MultipartFile>
    ): List<TranslateFileResponseDTO> {
        return coroutineScope {
            val requestList = image.map {
                async {
                    val file = fileService.saveEntity(fileService.saveImage(userDto, it))
                    val ocrResponse = ocrService.readAll(it)
                    val paragraph = ocrResponse.paragraphs
                    //번역을 위한 flatten
                    val stringBuilder = StringBuilder()
                    for (i in paragraph.indices) {
                        val p = paragraph[i]
                        if (i == paragraph.size - 1)
                            stringBuilder.append(p.content)
                        else
                            stringBuilder.append("${p.content}\n")
                    }
                    val paragraphResponse = ocrPostHandler.handleText(stringBuilder.toString())
                    val postHandleResponse = ocrPostHandler.handleText(ocrResponse.content)

                    val response = translateService.requestWebTranslate(TranslateRequestDTO("ko", lang, postHandleResponse))
                    val paraResponse = translateService.requestWebTranslate(TranslateRequestDTO("ko", lang, paragraphResponse))
                     paraResponse.result?.split("\n")?.forEachIndexed {index, content ->
                        paragraph[index].content = content
                    }
                    var convertImage : ConvertFileDTO? = null
                    if (isConvert)
                        convertImage = convertService.convertFile(it, paragraph)
                    TranslateFileResponseDTO(null, response.isSuccess, file, convertImage, response.from, response.target, response.origin, response.result)
                }
            }.toList()
            requestList.awaitAll().toList()
        }
    }

}