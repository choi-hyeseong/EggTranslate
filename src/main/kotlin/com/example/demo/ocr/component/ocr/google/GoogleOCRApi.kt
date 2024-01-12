package com.example.demo.ocr.component.ocr.google

import com.example.demo.ocr.component.ocr.OCRApi
import com.example.demo.ocr.component.ocr.google.format.BlockFormatter
import com.example.demo.ocr.exception.GoogleVisionException
import com.google.cloud.spring.vision.CloudVisionTemplate
import com.google.cloud.vision.v1.AnnotateImageResponse
import com.google.cloud.vision.v1.Feature
import org.springframework.core.io.ByteArrayResource
import org.springframework.web.multipart.MultipartFile

class GoogleOCRApi(private val cloudVisionTemplate: CloudVisionTemplate, private val blockFormatter: BlockFormatter) :
    OCRApi {
        //vision Template는 spring cloud dependency가 모두 있을경우 AutoConfig class에 의해 bean으로 제공됨.
        //기존 Config로 설정한 방식은 잘못 됨.

    override suspend fun readImage(file: MultipartFile): String {
        val builder: StringBuilder = StringBuilder()
        val response = cloudVisionTemplate.analyzeImage(ByteArrayResource(file.bytes), Feature.Type.TEXT_DETECTION)
        if (response.hasError())
            throw GoogleVisionException(response.error.message)
        else
            builder.append(handleResponse(response))

        return builder.toString()
    }

    suspend fun handleResponse(response: AnnotateImageResponse): String {
        return blockFormatter.format(response)
    }


}