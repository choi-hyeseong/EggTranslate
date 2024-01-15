package com.example.demo.signup.handler

import com.example.demo.common.response.Response
import com.example.demo.signup.exception.DuplicatedUsernameException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class DuplicatedExceptionHandler {
    @ExceptionHandler(DuplicatedUsernameException::class)
    fun handleDuplicatedUsername(e : DuplicatedUsernameException) = Response.ofFailure(e.message, null)
}