package com.example.demo.voca.handler

import com.example.demo.common.response.Response
import com.example.demo.voca.exception.VocaException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class VocaExceptionHandler {

    @ExceptionHandler(VocaException::class)
    fun handleException(e : VocaException) = ResponseEntity(Response.ofFailure(e.message, null), HttpStatus.BAD_REQUEST)
}