package com.example.demo.translate.web.prehandle

import com.example.demo.translate.web.prehandle.replacer.TranslatePreReplacer
import org.springframework.stereotype.Component

@Component
class TranslatePreHandler(val replacer : List<TranslatePreReplacer>) {

    fun preHandle(lang : String, input: String): String {
        return replacer.fold(input) {init, replacer -> replacer.replace(lang, init)}
    }
}