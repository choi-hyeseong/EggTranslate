package com.example.demo.common.handler

import com.example.demo.common.exception.ParameterNotValidException
import com.example.demo.common.response.Response
import com.example.demo.logger
import jakarta.validation.ConstraintViolationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class RequestExceptionHandler {

    private val log = logger()

    @ExceptionHandler(MissingServletRequestParameterException::class)
    fun handleMissingParameter(e : MissingServletRequestParameterException) : ResponseEntity<Response<Nothing>> {
        log.warn("Missing Parameter ${e.parameterName} : ${e.parameterType}")
        return ResponseEntity(Response.ofFailure("${e.parameterName} 값이 없습니다.", null), HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(ParameterNotValidException::class)
    protected fun handleNotValidException(e : ParameterNotValidException) : ResponseEntity<Response<Any>> {
        return ResponseEntity(Response.ofFailure(e.message, e.content), HttpStatus.INTERNAL_SERVER_ERROR)
    }

    @ExceptionHandler(ConstraintViolationException::class)
    fun handleConstraintException(e : ConstraintViolationException) : ResponseEntity<Response<Nothing>> {
        log.warn("Constraint Violation : ${e.message}")
        return ResponseEntity(Response.ofFailure(e.message, null), HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgumentException(e : IllegalArgumentException) : ResponseEntity<Response<Nothing>> {
        return ResponseEntity(Response.ofFailure(e.message, null), HttpStatus.INTERNAL_SERVER_ERROR)
    }
}