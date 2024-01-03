package com.example.demo.common.handler

import com.example.demo.logger
import com.example.demo.common.response.MessageResponse
import com.example.demo.exception.AzureRequestException
import com.example.demo.exception.GoogleVisionException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.reactive.function.client.WebClientResponseException

@RestControllerAdvice
class WebClientExceptionHandler {

    private val log = logger()

    @ExceptionHandler(WebClientResponseException::class)
    protected fun handleWebclientException(e : WebClientResponseException) : ResponseEntity<MessageResponse> {
        printError(e)
        return buildResponse()
    }

    @ExceptionHandler(GoogleVisionException::class)
    protected fun handleVisionException(e : GoogleVisionException)  : ResponseEntity<MessageResponse> {
        log.error("encountered vision exception : ${e.message}")
        return buildResponse()
    }

    @ExceptionHandler(AzureRequestException::class)
    protected fun handleAzureException(e : AzureRequestException) : ResponseEntity<MessageResponse> {
        log.error("encountered azure exception : ${e.message}")
        return buildResponse()
    }

    private fun buildResponse() : ResponseEntity<MessageResponse> {
        return ResponseEntity(MessageResponse(false, "해당 서비스 요청중 문제가 발생했습니다. 다시 시도해주세요."), HttpStatus.INTERNAL_SERVER_ERROR)
    }

    private fun printError(e : WebClientResponseException) {
        log.error("encountered 400 error(${e.request?.method}) : ${e.message}")
        log.error("headers : ${e.request?.headers}")
        log.error("response : ${e.responseBodyAsString}")
    }

}