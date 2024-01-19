package com.example.demo.translate.web.prehandle.replacer

interface TranslatePreReplacer {

    fun replace(lang : String, input : String) : String

}