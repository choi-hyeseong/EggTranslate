package com.example.demo.common.database.converter

class StringFlatter : AbstractStringFlatter<String>() {
    override fun convert(s: String): String {
        return s
    }

    override fun recover(s: String): String {
        return s
    }
}