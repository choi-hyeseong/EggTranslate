package com.example.demo.common.handler.log

import jakarta.servlet.*
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component
import org.springframework.web.util.ContentCachingResponseWrapper

@Component
class ServletWrappingFilter : Filter {

    override fun doFilter(p0: ServletRequest?, p1: ServletResponse?, p2: FilterChain?) {
        val request = p0 as HttpServletRequest
        val response = p1 as HttpServletResponse
        val wrapRequest = MultiAccessRequestWrapper(request)
        val wrapResponse = ContentCachingResponseWrapper(response)
        p2?.doFilter(wrapRequest, wrapResponse)
        //wrapResponse.copyBodyToResponse() //break point 사용가능
        //async controller는 listener를 통해 copy
        if (wrapRequest.isAsyncStarted)
            wrapRequest.asyncContext.addListener(AsyncResponseHandler(wrapResponse))
        else
            wrapResponse.copyBodyToResponse()
    }


    inner class AsyncResponseHandler(private val wrapResponse : ContentCachingResponseWrapper) : AsyncListener {
        override fun onComplete(p0: AsyncEvent?) {
            wrapResponse.copyBodyToResponse()
        }

        override fun onTimeout(p0: AsyncEvent?) {
        }

        override fun onError(p0: AsyncEvent?) {
        }

        override fun onStartAsync(p0: AsyncEvent?) {
        }

    }




}