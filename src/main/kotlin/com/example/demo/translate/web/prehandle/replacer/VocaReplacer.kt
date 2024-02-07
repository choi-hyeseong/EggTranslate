package com.example.demo.translate.web.prehandle.replacer

import com.example.demo.voca.service.VocaService
import kotlinx.coroutines.*
import org.springframework.stereotype.Component

@Component
class VocaReplacer(
    val service: VocaService
) : TranslatePreReplacer {

    override fun replace(lang: String, input: String) = runBlocking {
        withContext(Dispatchers.IO) {
            service.findAllContainingVoca(lang, input).fold(input) { data, voca
                ->
                data.replace(voca.origin, voca.translate)
            }
        }
    }
}