package com.example.demo.exception

class ParameterNotValidException(message : String, val content: String?) : Exception(message)