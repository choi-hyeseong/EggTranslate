package com.example.demo.common.handler

import com.example.demo.common.response.Response
import com.example.demo.exception.ImageNotValidException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ValidExceptionHandler {

    @ExceptionHandler(ImageNotValidException::class)
    protected fun handleImageNotValid(e : ImageNotValidException) : ResponseEntity<Response<Nothing>> {
        return ResponseEntity(Response.ofFailure(e.message, null), HttpStatus.INTERNAL_SERVER_ERROR)
    }
}