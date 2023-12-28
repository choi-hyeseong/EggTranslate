package com.example.demo.component.ocr

import com.example.demo.component.ocr.format.OCRFormatter
import com.example.demo.logger
import com.google.cloud.vision.v1.AnnotateImageResponse
import com.google.cloud.vision.v1.Feature
import org.springframework.cloud.gcp.vision.CloudVisionTemplate
import org.springframework.core.io.ByteArrayResource
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile

@Component
class GoogleOCRApi(private val cloudVisionTemplate: CloudVisionTemplate, private val ocrFormatter: OCRFormatter) : OCRApi {

    private val log = logger()

    override suspend fun readImage(file: MultipartFile): String {
        val builder: StringBuilder = StringBuilder()
        val response = cloudVisionTemplate.analyzeImage(ByteArrayResource(file.bytes), Feature.Type.DOCUMENT_TEXT_DETECTION)
        if (response.hasError())
            log.warn("Google Vision encountered an error : {}", response.error.message)
        else
            builder.append(handleResponse(response))

        return builder.toString()
    }

    fun handleResponse(response: AnnotateImageResponse): String {
        return ocrFormatter.format(response)
    }


}