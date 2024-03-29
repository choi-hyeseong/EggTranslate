package com.example.demo.image.service

import com.example.demo.convertOrNull
import com.example.demo.file.service.FileService
import com.example.demo.file.dto.ConvertFileDTO
import com.example.demo.image.dto.ImageDTO
import com.example.demo.logger
import com.example.demo.ocr.component.ocr.model.Paragraph
import com.example.demo.ocr.component.posthandle.OCRPostHandler
import com.example.demo.ocr.service.OCRService
import com.example.demo.translate.auto.dto.TranslateFileData
import com.example.demo.translate.auto.dto.TranslateFileResponseDTO
import com.example.demo.translate.auto.dto.TranslateResultData
import com.example.demo.translate.auto.dto.TranslateResultResponseDTO
import com.example.demo.translate.service.TranslateService
import com.example.demo.translate.web.dto.TranslateRequestDTO
import com.example.demo.member.user.dto.UserDto
import com.example.demo.member.user.service.UserService
import com.example.demo.member.parent.service.ParentService
import com.example.demo.voca.service.VocaService
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
    private val vocaService: VocaService,
    private val ocrPostHandler: OCRPostHandler
) {

    private val log = logger()

    suspend fun handleImage(imageDTO: ImageDTO): TranslateResultResponseDTO {
        //save image
        val user = imageDTO.userId.convertOrNull { userService.getUser(it) }
        //read parallel logic
        val response = requestImage(imageDTO.image ?: false, imageDTO.lang, user, imageDTO.file)
        // return translated string
        val childDTO = parentService.findByChildIdOrNull(imageDTO.userId, imageDTO.childId)
        return translateService.saveTranslate(user, childDTO, response)
    }


    private suspend fun requestImage(
        isConvert: Boolean,
        lang: String,
        userDto: UserDto?,
        image: List<MultipartFile>
    ): List<TranslateFileResponseDTO> {
        return coroutineScope {
            val requestList = image.map {
                async {
                    val file = fileService.saveEntity(fileService.saveFile(userDto, it))
                    val ocrResponse = ocrService.readAll(it)
                    val paragraph = ocrResponse.paragraphs
                    //번역을 위한 flatten
                    val postHandleResponse = ocrPostHandler.handleText(ocrResponse.content)
                    //여기서 찾으면 2번 호출하는데.. (Translate Pre Handler)
                    val allVocaDesc = vocaService.findAllContainingVoca(lang, postHandleResponse)
                    val response = translateService.requestWebTranslate(TranslateRequestDTO("ko", lang, postHandleResponse))

                    val fileData = TranslateFileData(file, getConvertImage(isConvert, lang, it, paragraph, userDto), null, null)
                    val translateData = TranslateResultData(allVocaDesc, response.from, response.to, postHandleResponse, response.result)

                    TranslateFileResponseDTO(null, response.isSuccess, fileData, translateData)
                    //결과 반환시에는 origin에는 replace 된 값 보내줘선 안됨. 오직 origin 데이터.
                }
            }.toList()
            requestList.awaitAll().toList()
        }
    }

    //image 요청이 false면 null을 리턴
    private suspend fun getConvertImage(
        isRequested: Boolean,
        lang: String,
        file: MultipartFile,
        paragraphs: List<Paragraph>,
        userDto: UserDto?
    ): ConvertFileDTO? {
        if (!isRequested)
            return null
        val paragraphResponse = ocrPostHandler.handleText(getFlattenParagraphContent(paragraphs))
        val paraResponse =
            translateService.requestWebTranslate(TranslateRequestDTO("ko", lang, paragraphResponse))
        paraResponse.result?.split("\n")?.forEachIndexed { index, content ->
            paragraphs[index].content = content
        }
        return convertService.convertFile(file, paragraphs, userDto)
    }

    private fun getFlattenParagraphContent(paragraphs: List<Paragraph>): String {
        val stringBuilder = StringBuilder()
        for (i in paragraphs.indices) {
            val p = paragraphs[i]
            if (i == paragraphs.size - 1)
                stringBuilder.append(p.content)
            else
                stringBuilder.append("${p.content}\n")
        }
        return stringBuilder.toString()
    }

}