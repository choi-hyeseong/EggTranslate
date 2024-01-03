package com.example.demo.component.ocr.google

import com.example.demo.component.ocr.OCRApi
import com.example.demo.component.ocr.google.format.BlockFormatter
import com.example.demo.exception.GoogleVisionException
import com.google.cloud.vision.v1.AnnotateImageResponse
import com.google.cloud.vision.v1.Feature
import org.springframework.cloud.gcp.vision.CloudVisionTemplate
import org.springframework.core.io.ByteArrayResource
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile

@Component
class GoogleOCRApi(private val cloudVisionTemplate: CloudVisionTemplate, private val blockFormatter: BlockFormatter) :
    OCRApi {

    override suspend fun readImage(file: MultipartFile): String {
        val builder: StringBuilder = StringBuilder()
        val response = cloudVisionTemplate.analyzeImage(ByteArrayResource(file.bytes), Feature.Type.TEXT_DETECTION)
        if (response.hasError())
            throw GoogleVisionException(response.error.message)
        else
            builder.append(handleResponse(response))

        return builder.toString()
    }

    fun handleResponse(response: AnnotateImageResponse): String {
        return blockFormatter.format(response)
    }


}