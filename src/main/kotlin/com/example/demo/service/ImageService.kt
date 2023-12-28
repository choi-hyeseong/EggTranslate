package com.example.demo.service

import com.example.demo.dto.translate.TranslateRequestDTO
import com.example.demo.dto.translate.TranslateResponseDTO
import com.example.demo.logger
import com.example.demo.response.ObjectResponse
import kotlinx.coroutines.*
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.FileOutputStream

@Service
class ImageService(private val ocrService: OCRService, private val translateService: TranslateService) {

    @Value("\${image-path}")
    private lateinit var outPath: String
    private val log = logger()

    suspend fun handleImage(image: List<MultipartFile>): ObjectResponse<List<TranslateResponseDTO>> {
        //save image
        image.forEach { saveImage(it) }
        return translateService.translate(image
                .map { TranslateRequestDTO("ko", "en", ocrService.readImage(it)) }
                .toList()
        )
    }

    suspend fun saveImage(image: MultipartFile) {
        //이미지 저장하고나서 ocr이 요청될 필요X (parallel하게)
        withContext(Dispatchers.IO) {
            val ext = image.originalFilename?.split(".")?.get(1)
            val path = outPath.plus("\\${System.currentTimeMillis()}.$ext") //value로 형식 부여받기?
            val outputStream = FileOutputStream(path)
            outputStream.write(image.bytes)
            outputStream.close()
            log.info("image file saved to $path")
        }

    }


}