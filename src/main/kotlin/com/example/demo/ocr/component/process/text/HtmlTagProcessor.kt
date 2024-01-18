package com.example.demo.ocr.component.process.text

import org.springframework.stereotype.Component

@Component
class HtmlTagProcessor : OCRTextProcessor {
    // ocr로 읽힌 문자열중 selected 이런거 제거하기
    override fun postHandle(input: String): String {
        return input.replace(":selected:", "").replace(":unselected:", "")
    }
}