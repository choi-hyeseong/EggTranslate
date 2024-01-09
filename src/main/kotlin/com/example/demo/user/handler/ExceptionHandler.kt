package com.example.demo.user.handler

import com.example.demo.common.response.Response
import com.example.demo.user.basic.exception.UserNotFoundException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ExceptionHandler {

    @ExceptionHandler(UserNotFoundException::class)
    fun handleUserNotFound(e : UserNotFoundException) = Response.ofFailure(e.message, e.id)

}