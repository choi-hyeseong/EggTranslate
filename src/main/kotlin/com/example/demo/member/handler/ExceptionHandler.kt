package com.example.demo.member.handler

import com.example.demo.common.response.Response
import com.example.demo.member.exception.ParentException
import com.example.demo.member.exception.UserException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ExceptionHandler {

    @ExceptionHandler(UserException::class)
    fun handleUserNotFound(e : UserException) = ResponseEntity(Response.ofFailure(e.message, null), HttpStatus.BAD_REQUEST)

    @ExceptionHandler(ParentException::class)
    fun handleUserException(e : ParentException) = ResponseEntity(Response.ofFailure(e.message, e.id), HttpStatus.BAD_REQUEST)
}