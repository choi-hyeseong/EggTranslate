package com.example.demo.translate.handler

import com.example.demo.common.response.Response
import com.example.demo.translate.exception.TranslateException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class TranslateExceptionHandler {

    @ExceptionHandler(TranslateException::class)
    fun handleTranslateException(e : TranslateException) = Response.ofFailure(e.message, null)
}