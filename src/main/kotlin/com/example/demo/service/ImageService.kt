package com.example.demo.service

import com.example.demo.common.response.Response
import com.example.demo.dto.translate.TranslateRequestDTO
import com.example.demo.dto.translate.TranslateResponseDTO
import com.example.demo.logger
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

    suspend fun handleImage(image: List<MultipartFile>): Response<List<TranslateResponseDTO>> {
        //save image
        saveImage(image)
        return coroutineScope {
            //read parallel logic
            val requestList = image.map {
                async {
                    ocrService.readImage(it)
                }
            }.toList()
            val ocrData = requestList.awaitAll().map { TranslateRequestDTO("ko", "en", it) }.toList()
            // return translated string
            translateService.translate(ocrData)
        }

    }

    suspend fun saveImage(image: List<MultipartFile>) {
        //이미지 저장하고나서 ocr이 요청될 필요X (parallel하게)
        coroutineScope {
            image.forEach { image ->
                launch {
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
        }

    }


}