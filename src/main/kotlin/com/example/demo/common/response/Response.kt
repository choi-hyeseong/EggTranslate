package com.example.demo.common.response

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL) // Null 값인 필드 제외
class Response<T>(
    val isSuccess: Boolean,
    val message: String?,
    val data: T?
) {
    companion object {

        fun <T> of(isSuccess: Boolean, message: String?, data : T?) : Response<T> {
            return Response(isSuccess, message, data)
        }

        fun <T> ofSuccess(message: String?, data: T?): Response<T> {
            return of(true, message, data)
        }

        fun <T> ofFailure(message: String?, data : T?) : Response<T> {
            return of(false, message, data)
        }
    }
}