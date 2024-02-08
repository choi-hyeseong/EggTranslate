package com.example.demo.auth.jwt.exception

class JWTException(message : String, val accessToken : String?, val wrappingException : Exception?) : Exception(message) {

    constructor(message: String, accessToken: String?) : this(message, accessToken, null)
}