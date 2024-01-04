package com.example.demo.ocr.handler

import com.example.demo.common.response.Response
import com.example.demo.logger
import com.example.demo.ocr.exception.AzureRequestException
import com.example.demo.ocr.exception.GoogleVisionException
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class OCRExceptionHandler {

    private val log = logger()

    @ExceptionHandler(GoogleVisionException::class)
    protected fun handleVisionException(e : GoogleVisionException)  : ResponseEntity<Response<Nothing>> {
        log.error("encountered vision exception : ${e.message}")
        return buildResponse()
    }

    @ExceptionHandler(AzureRequestException::class)
    protected fun handleAzureException(e : AzureRequestException) : ResponseEntity<Response<Nothing>> {
        log.error("encountered azure exception : ${e.message}")
        return if (e.code == HttpStatus.BAD_REQUEST)
            buildResponse(e.code, e.message ?: "")
        else
            buildResponse()
    }

    private fun buildResponse() : ResponseEntity<Response<Nothing>> {
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "해당 서비스 요청중 문제가 발생했습니다. 다시 시도해주세요.")
    }

    private fun buildResponse(code: HttpStatusCode, message: String) : ResponseEntity<Response<Nothing>> {
        return ResponseEntity(Response.ofFailure(message, null), code)
    }
}