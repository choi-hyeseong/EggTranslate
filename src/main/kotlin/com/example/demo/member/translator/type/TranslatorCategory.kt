package com.example.demo.member.translator.type

import com.example.demo.logger

enum class TranslatorCategory(val value : String) {

    EDUCATION("교육"), CULTURE("문화"), OTHER("기타");


    override fun toString(): String {
        return value
    }

    companion object {

        private val log = logger()

        fun fromString(s : String) : TranslatorCategory {
            val find = entries.find { it.value.equals(s, ignoreCase = true) }
            if (find == null) {
                log.warn("Enum $s 를 찾을 수 없습니다. '기타'로 반환합니다.")
                return OTHER
            }
            else
                return find

        }
    }
}