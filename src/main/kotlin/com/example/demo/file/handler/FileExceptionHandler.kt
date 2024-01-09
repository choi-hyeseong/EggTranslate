package com.example.demo.file.handler

import com.example.demo.common.response.Response
import com.example.demo.file.exception.FileException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class FileExceptionHandler {

    @ExceptionHandler
    fun handleFileException(e : FileException) = Response.ofFailure(e.message, null)
}