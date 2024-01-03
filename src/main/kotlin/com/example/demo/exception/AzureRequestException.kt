package com.example.demo.exception

import org.springframework.http.HttpStatusCode

class AzureRequestException(val code : HttpStatusCode, message : String) : Exception(message)