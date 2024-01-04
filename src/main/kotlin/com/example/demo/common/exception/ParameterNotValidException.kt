package com.example.demo.common.exception

class ParameterNotValidException(message : String, val content: String?) : Exception(message)