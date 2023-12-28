package com.example.demo.component.ocr.format

import com.google.cloud.vision.v1.AnnotateImageResponse
import org.springframework.stereotype.Component

@Component
class NormalFormatter : OCRFormatter {
    override fun format(response: AnnotateImageResponse): String {
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