package com.example.demo.signup.handler

import com.example.demo.common.response.Response
import com.example.demo.signup.exception.RegistrationFailedException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class RegistrationExceptionHandler {

    //왠만하면 잘 호출 안될듯 (db 엔티티 unique 위반 exception이 먼저 발생)
    @ExceptionHandler(RegistrationFailedException::class)
    fun handleRegistrationFailed(e : RegistrationFailedException) = ResponseEntity(Response.ofFailure(e.message, null), HttpStatus.BAD_REQUEST)
}