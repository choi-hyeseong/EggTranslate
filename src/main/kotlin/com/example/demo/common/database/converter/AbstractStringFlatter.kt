package com.example.demo.common.database.converter

import jakarta.persistence.AttributeConverter

//기존 List형식의 String들을 String으로 직렬화
abstract class AbstractStringFlatter<T> : AttributeConverter<List<T>, String> {
    override fun convertToDatabaseColumn(p0: List<T>?): String {
        return if (p0.isNullOrEmpty())
             ""
        else
             p0.joinToString(separator = ", ")
    }

    override fun convertToEntityAttribute(p0: String?): List<T> {
        return if (p0.isNullOrEmpty())
            mutableListOf()
        else
            p0.split(",").map { convert(it.trim()) }
    }

    protected abstract fun convert(s : String) : T
}

