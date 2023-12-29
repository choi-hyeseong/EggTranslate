package com.example.demo.common.config

import com.example.demo.common.handler.log.ServletWrappingFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfig(private val loggingFilter: ServletWrappingFilter) {

    @Bean
    fun filterChain(httpSecurity: HttpSecurity): SecurityFilterChain {
        return httpSecurity
            .csrf { csrf -> csrf.disable() }
            .formLogin { login -> login.disable() }
            .authorizeHttpRequests { request ->
                request.requestMatchers(HttpMethod.POST, "/api/**").permitAll()
            }
            .addFilterBefore(loggingFilter, BasicAuthenticationFilter::class.java)
            .build()
    }
}