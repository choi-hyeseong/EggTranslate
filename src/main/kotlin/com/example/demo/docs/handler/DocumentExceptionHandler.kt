package com.example.demo.docs.handler

import com.example.demo.common.response.Response
import com.example.demo.docs.exception.DocumentException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class DocumentExceptionHandler {

    @ExceptionHandler(DocumentException::class)
    fun handleDocumentException(e : DocumentException) : Response<Nothing> {
        return Response.ofFailure(e.localizedMessage ?: e.message, null)
    }
}