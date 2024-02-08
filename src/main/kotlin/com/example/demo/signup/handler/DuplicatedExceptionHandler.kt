package com.example.demo.signup.handler

import com.example.demo.common.response.Response
import com.example.demo.signup.exception.DuplicatedException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class DuplicatedExceptionHandler {
    @ExceptionHandler(DuplicatedException::class)
    fun handleDuplicatedUsername(e : DuplicatedException) = ResponseEntity(Response.ofFailure(e.message, null), HttpStatus.BAD_REQUEST)
}