package com.example.demo.component.ocr.format

import com.google.cloud.vision.v1.*
import com.google.cloud.vision.v1.TextAnnotation.TextProperty
import org.springframework.stereotype.Component

@Component
class BlockFormatter : OCRFormatter {
    // https://stackoverflow.com/questions/57071788/google-vision-api-text-detection-display-words-by-block참고
    override fun format(response: AnnotateImageResponse): String {
        return getTextBlocks(response).joinToString(separator = "\n")
    }

    fun getTextBlocks(response: AnnotateImageResponse) : List<String> {
        val list : MutableList<String> = mutableListOf()
        for (page in response.fullTextAnnotation.pagesList) {
            for (block in page.blocksList) {
                list.add(getTextInBlock(block))
            }
        }
        return list
    }

    fun getTextInBlock(block : Block) : String {
        val builder = StringBuilder()
        for (paragraph in block.paragraphsList) {
            for (word in paragraph.wordsList) {
                for (symbol in word.symbolsList) {
                    builder.append(symbol.text)
                    if (symbol.property is TextProperty && symbol.property.detectedBreak != null) {
                        when (symbol.property.detectedBreak.type) {
                            TextAnnotation.DetectedBreak.BreakType.LINE_BREAK -> builder.append("\n")
                            TextAnnotation.DetectedBreak.BreakType.SPACE -> builder.append(" ")
                            else -> {}
                        }
                    }
                }
            }
        }
        return builder.toString()
    }

}

