package com.example.demo.voca.handler

import com.example.demo.common.response.Response
import com.example.demo.voca.exception.VocaException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class VocaExceptionHandler {

    @ExceptionHandler(VocaException::class)
    fun handleException(e : VocaException) = Response.ofFailure(e.message, null)
}