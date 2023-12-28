package com.example.demo.common.handler.log.print

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import org.springframework.stereotype.Component

interface Converter {
    fun convert(obj : ByteArray) : String
}

@Component
class NormalConverter : Converter {
    override fun convert(obj: ByteArray): String {
        return String(obj, Charsets.UTF_8)
    }
}

@Component
class JsonConverter(private val normalConverter: NormalConverter) : Converter {
    private val gson : Gson = GsonBuilder().setPrettyPrinting().create()

    override fun convert(obj: ByteArray): String {
        return gson.toJson(JsonParser.parseString(normalConverter.convert(obj)))
    }

}