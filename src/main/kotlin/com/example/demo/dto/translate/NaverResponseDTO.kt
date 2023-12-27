package com.example.demo.dto.translate

class NaverResponseDTO {

    lateinit var message: Message
    lateinit var origin: String

    fun toResponseDTO(): TranslateResponseDTO {
        return TranslateResponseDTO(message.result.srcLangType, message.result.tarLangType, origin, message.result.translatedText)
    }

    class Message {
        lateinit var result: Result;
    }

    class Result {
        lateinit var srcLangType: String
        lateinit var tarLangType: String
        lateinit var translatedText: String
        lateinit var engineType: String
        lateinit var pivot: String
        lateinit var dict: String
        lateinit var tarDict: String
    }


}
