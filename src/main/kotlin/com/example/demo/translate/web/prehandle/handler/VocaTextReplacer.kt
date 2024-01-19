package com.example.demo.translate.web.prehandle.handler

import com.example.demo.voca.service.VocaService
import kotlinx.coroutines.*
import org.springframework.stereotype.Component

@Component
class VocaTextReplacer(
    val lang: String,
    val service: VocaService
) : AbstractTranslatePreHandler(lang) {
    override fun postHandle(input: String) = runBlocking {
        withContext(Dispatchers.IO) {
            service.findAll(lang).fold(input) { data, voca
                -> data.replace(voca.origin, voca.translate) }
        }
    }
}