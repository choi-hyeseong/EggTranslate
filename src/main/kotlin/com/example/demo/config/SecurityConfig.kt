package com.example.demo.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class SecurityConfig {

    @Bean
    fun filterChain(httpSecurity: HttpSecurity) : SecurityFilterChain {
        return httpSecurity
                .csrf { csrf -> csrf.disable() }
                .formLogin { login -> login.disable() }
                .authorizeHttpRequests { request ->
            request.requestMatchers(HttpMethod.POST, "/api/**").permitAll()
        }.build()
    }
}