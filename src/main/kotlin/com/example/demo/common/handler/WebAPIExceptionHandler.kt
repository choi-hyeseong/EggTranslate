package com.example.demo.common.handler

import com.example.demo.common.response.Response
import com.example.demo.exception.AzureRequestException
import com.example.demo.exception.GoogleVisionException
import com.example.demo.logger
import com.google.api.client.googleapis.json.GoogleJsonResponseException
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.reactive.function.client.WebClientResponseException

@RestControllerAdvice
class WebAPIExceptionHandler {

    private val log = logger()

    @ExceptionHandler(WebClientResponseException::class)
    protected fun handleWebclientException(e : WebClientResponseException) : ResponseEntity<Response<Nothing>> {
        printError(e)
        return buildResponse()
    }

    @ExceptionHandler(GoogleVisionException::class)
    protected fun handleVisionException(e : GoogleVisionException)  : ResponseEntity<Response<Nothing>>{
        log.error("encountered vision exception : ${e.message}")
        return buildResponse()
    }

    @ExceptionHandler(AzureRequestException::class)
    protected fun handleAzureException(e : AzureRequestException) :ResponseEntity<Response<Nothing>> {
        log.error("encountered azure exception : ${e.message}")
        return if (e.code == HttpStatus.BAD_REQUEST)
            buildResponse(e.code, e.message ?: "")
        else
            buildResponse()
    }

    @ExceptionHandler(GoogleJsonResponseException::class)
    protected fun handleGoogleException(e : GoogleJsonResponseException) : ResponseEntity<Response<Nothing>> {
        log.error("encountered google translation exception : ${e.details.message}")
        return buildResponse()
    }

    private fun buildResponse() : ResponseEntity<Response<Nothing>> {
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "해당 서비스 요청중 문제가 발생했습니다. 다시 시도해주세요.")
    }

    private fun buildResponse(code: HttpStatusCode, message: String) : ResponseEntity<Response<Nothing>> {
        return ResponseEntity(Response.ofFailure(message, null), code)
    }

    private fun printError(e : WebClientResponseException) {
        log.error("encountered 400 error(${e.request?.method}) : ${e.message}")
        log.error("headers : ${e.request?.headers}")
        log.error("response : ${e.responseBodyAsString}")
    }

}