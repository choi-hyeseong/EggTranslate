package com.example.demo.common.response

import com.fasterxml.jackson.annotation.JsonInclude
import io.swagger.v3.oas.annotations.media.Schema

@JsonInclude(JsonInclude.Include.NON_NULL) // Null 값인 필드 제외
class Response<T>(
    @Schema(name = "isSuccess", description = "성공 여부를 반환합니다.")
    val isSuccess: Boolean,
    @Schema(name = "message", description = "서버의 응답 메시지를 반환합니다. 비어있을 수 있습니다.", required = false)
    val message: String?,
    @Schema(name = "data", description = "응답값에 담길 데이터입니다. 비어있을 수 있습니다.", required = false)
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