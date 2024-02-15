package com.example.demo.auth.security.config

import com.example.demo.auth.security.filter.JwtAuthenticationFilter
import com.example.demo.common.handler.log.ServletWrappingFilter
import com.example.demo.member.exception.UserException
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfig(private val loggingFilter: ServletWrappingFilter, private val jwtAuthenticationFilter: JwtAuthenticationFilter) {

    @Bean
    fun filterChain(httpSecurity: HttpSecurity): SecurityFilterChain {
        return httpSecurity
            .csrf { csrf -> csrf.disable() }
            .formLogin { login -> login.disable() }
            .sessionManagement { session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .authorizeHttpRequests { request ->
//                request.requestMatchers("/api/admin/**").hasAuthority("ADMIN") //role 대신 authority 사용
//                request.requestMatchers("/api/voca/load").hasAuthority("ADMIN")
//                request.requestMatchers("/api/voca/**").permitAll()
//                request.requestMatchers("/api/user/**").authenticated()
//                request.requestMatchers("/api/document/**").permitAll()
//                request.requestMatchers("/api/file/**").permitAll()
//                request.requestMatchers("/api/board/**").authenticated()
//                request.requestMatchers("/api/register/**").permitAll()

                request.requestMatchers("/api/**").permitAll()
                request.requestMatchers("/error").permitAll()
            }
            .addFilterBefore(loggingFilter, BasicAuthenticationFilter::class.java)
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)
            .exceptionHandling { exception ->
                exception.authenticationEntryPoint(BasicAuthenticationEntryPoint()) //미 인증시 403대신 401 발생
            }
            .build()
    }


}

//확장함수로 구현해서 로그인 되지 않은 유저의 경우 exception 발생
fun Authentication?.getUserOrThrow() : UserDetails {
    return if (this != null && this.principal != null)
        this.principal as UserDetails
    else
        throw UserException("로그인 후 이용해주세요.")
}

fun Authentication?.getUserOrNull() : UserDetails? {
    return if (this != null && this.principal != null)
        this.principal as UserDetails
    else
       null
}