package com.example.demo.component.ocr

import com.example.demo.logger
import com.google.cloud.vision.v1.AnnotateImageRequest
import com.google.cloud.vision.v1.AnnotateImageResponse
import com.google.cloud.vision.v1.Feature
import com.google.cloud.vision.v1.Image
import com.google.cloud.vision.v1.ImageAnnotatorClient
import com.google.protobuf.ByteString
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.cloud.gcp.vision.CloudVisionTemplate
import org.springframework.core.io.ByteArrayResource
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import java.util.ArrayList

@Component
class GoogleOCRApi(private val cloudVisionTemplate: CloudVisionTemplate) : OCRApi {

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
        // todo line per line. 개행은 \n으로, 공백은 Tab (스페이스 4개)
        val builder = StringBuilder()
        //text detection이므로 text annotations list
        response.textAnnotationsList.forEach {
            val descriptions: List<String> = it.allFields
                    .filter { (key, _) -> key.toString().contains("description") }
                    .map { (_, value) -> value.toString() }
                    .toList()
            descriptions.forEach { desc ->
                builder.append(desc.plus(" "))
            }
        }
        return builder.toString()
    }


}