package com.example.demo.ocr.component.posthandle

import org.springframework.stereotype.Component

@Component
class OCRPostHandler(private val processors: List<TextReplacer>) {

    //각 후처리에 필요한 컴포넌트의 집약체.
    fun handleText(input: String): String {
        return processors.fold(input) { param, processor -> processor.postHandle(param) }
    }
}