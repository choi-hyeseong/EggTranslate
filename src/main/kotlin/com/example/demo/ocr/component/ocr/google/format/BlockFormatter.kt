package com.example.demo.ocr.component.ocr.google.format

import com.google.cloud.vision.v1.AnnotateImageResponse
import com.google.cloud.vision.v1.Block
import com.google.cloud.vision.v1.TextAnnotation
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.regex.Pattern

@Component
class BlockFormatter : OCRFormatter {

    @Value("\${ocr.ignore_unnecessary}")
    private var ignoreUnnecessaryData : Boolean = false
    private val pattern : Pattern = Pattern.compile(".*[ㄱ-ㅎㅏ-ㅣ가-힣]+.*")

    // https://stackoverflow.com/questions/57071788/google-vision-api-text-detection-display-words-by-block참고
    override fun format(response: AnnotateImageResponse): String {
        return getTextBlocks(response).joinToString(separator = "\n")
    }

    fun getTextBlocks(response: AnnotateImageResponse): List<String> {
        /*
        val list: MutableList<String> = mutableListOf()
        for (page in response.fullTextAnnotation.pagesList) {
            for (block in page.blocksList) {
                list.add(getTextInBlock(block))
            }
        }
        return list
         */
        return response.fullTextAnnotation.pagesList
                .flatMap { page -> page.blocksList }
                .map { block -> getTextInBlock(block) }
                .filter { text ->
                    if (ignoreUnnecessaryData)
                        pattern.matcher(text).matches()
                    else
                        true
                }
                .toList()

    }

    fun getTextInBlock(block: Block): String {
        val builder = StringBuilder()
        /*
        첫번째 flatMap -> List<Paragraph>를 List<Word>로
        두번째 flatMap -> List<Word>를 List<Symbol>로
        기존 : {PARAGRAPH1, PARAGRAPH2, PARAGRAPH3..} : List<Paragraph>
        map -> {List<WORD>1, List<WORD>2....} : List<List<Word>>
        flatMap -> {WORD1-1, WORD1-2, WORD2-1, WORD2-2...} : List<Word>
        https://dev-kani.tistory.com/33 참조
         */
        val symbols = block.paragraphsList
                .flatMap { paragraph -> paragraph.wordsList }
                .flatMap { word -> word.symbolsList }
                .toList()
        symbols.forEach {
            builder.append(it.text)
            if (TextAnnotation.DetectedBreak.BreakType.SPACE == it.property.detectedBreak.type)
                builder.append(" ")
        }
        /*
        for (paragraph in block.paragraphsList) {
            for (word in paragraph.wordsList) {
                for (symbol in word.symbolsList) {
                    builder.append(symbol.text)
                    if (symbol.property is TextProperty && symbol.property.detectedBreak != null) {
                        if (TextAnnotation.DetectedBreak.BreakType.SPACE == symbol.property.detectedBreak.type)
                            builder.append(" ")

                    }
                }
            }
        }*/
        return builder.toString()
    }

}

