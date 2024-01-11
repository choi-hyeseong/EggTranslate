package com.example.demo.translate.handler

import com.example.demo.common.response.Response
import com.example.demo.translate.manual.exception.ManualException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ManualExceptionHandler {

    @ExceptionHandler(ManualException::class)
    fun handleManualException(e : ManualException) = Response.ofFailure(e.message, null)
}