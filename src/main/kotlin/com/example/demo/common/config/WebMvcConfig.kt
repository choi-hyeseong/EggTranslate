package com.example.demo.common.config

import com.example.demo.common.handler.log.LoggingInterceptor
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer


//interceptor는 mvc config에 넣어야 작동
@Configuration
class WebMvcConfig(private val loggingInterceptor: LoggingInterceptor) : WebMvcConfigurer {

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(loggingInterceptor).addPathPatterns("/**")
    }
}