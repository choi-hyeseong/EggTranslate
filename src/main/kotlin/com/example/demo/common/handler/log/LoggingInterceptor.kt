package com.example.demo.common.handler.log

import com.example.demo.common.handler.log.print.JsonConverter
import com.example.demo.logger
import jakarta.servlet.DispatcherType
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import org.springframework.web.util.ContentCachingResponseWrapper
import java.lang.Exception

@Component
class LoggingInterceptor(private val converter: JsonConverter) : HandlerInterceptor {

    private val log = logger()

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        // AsyncRequest의 경우 preHandle이 2번 호출됨.
        // https://godd.tistory.com/83 참조
        if (DispatcherType.REQUEST == request.dispatcherType) {
            if (request.contentType.contains("application/json")) {
                val wrapRequest = request as? MultiAccessRequestWrapper
                wrapRequest?.let {
                    val body = converter.convert(it.getContents())
                    log.info(
                        "---> [REQUEST] {} {} {} BODY\n{}",
                        request.method,
                        request.requestURL,
                        request.remoteAddr,
                        body
                    )
                }
            } else
                log.info("---> [REQUEST] {} {} {}", request.method, request.requestURL, request.remoteAddr)
        }
        return super.preHandle(request, response, handler)
    }

    override fun afterCompletion(request: HttpServletRequest, response: HttpServletResponse, handler: Any, ex: Exception?) {
        val wrapResponse = response as? ContentCachingResponseWrapper
        wrapResponse?.let {
            val body = converter.convert(it.contentAsByteArray)
            log.info("<--- [RESPONSE] {} BODY\n{}", response.status, body)
            super.afterCompletion(request, response, handler, ex)
        }


    }

}