package com.example.demo.handler.log

import jakarta.servlet.AsyncEvent
import jakarta.servlet.AsyncListener
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.web.util.ContentCachingResponseWrapper

@Component
class ServletWrappingFilter : OncePerRequestFilter() {
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        val wrapRequest = MultiAccessRequestWrapper(request)
        val wrapResponse = ContentCachingResponseWrapper(response)
        filterChain.doFilter(wrapRequest, wrapResponse)
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