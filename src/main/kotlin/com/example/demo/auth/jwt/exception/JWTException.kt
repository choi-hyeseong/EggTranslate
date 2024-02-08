package com.example.demo.auth.jwt.exception

class JWTException(message : String, val accessToken : String?, val wrappingException : Exception?) : Exception(message) {

    companion object {
        fun wrap(e : Exception, accessToken: String?) : JWTException {
            return if (e is JWTException)
                e
            else
                JWTException("잘못된 유저 정보입니다. 다시 토큰을 발급받아주세요.", accessToken, e)
        }
    }

    constructor(message: String, accessToken: String?) : this(message, accessToken, null)

}