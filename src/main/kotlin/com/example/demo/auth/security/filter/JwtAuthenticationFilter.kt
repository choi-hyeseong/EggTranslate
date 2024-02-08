package com.example.demo.auth.security.filter

import com.example.demo.auth.jwt.exception.JWTException
import com.example.demo.auth.service.UserAuthenticateService
import com.example.demo.common.response.Response
import com.example.demo.logger
import com.fasterxml.jackson.databind.ObjectMapper
import com.google.firebase.ErrorCode
import io.jsonwebtoken.Jwt
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.GenericFilterBean
import java.io.IOException


@Component
class JwtAuthenticationFilter(private val userAuthenticateService: UserAuthenticateService) : GenericFilterBean() {

    val objectMapper = ObjectMapper()
    private val log = logger()

    //만약 토큰이 정상적인경우 - 정상적 플로우
    //토큰이 만료되거나 exception 발생 (토큰을 제공한경우) - 해당 요청에 적합한 응답 제공
    //토큰을 제공 안한경우 - 스프링 시큐리티 예외
    override fun doFilter(request: ServletRequest?, response: ServletResponse?, chain: FilterChain?) {
        val token = resolveTokenFromHeader(request as HttpServletRequest)
        try {
            SecurityContextHolder.getContext().authentication =
                userAuthenticateService.authenticate(token) //유효한 토큰일경우 authentication을 반환함.
        }
        catch (e: JWTException) {
            if (e.accessToken != null) { //토큰이 있는경우
                sendTokenInvalid(response as HttpServletResponse?, e)
                return
            }
        }
        chain?.doFilter(request, response)


    }

    private fun sendTokenInvalid(response: HttpServletResponse?, e: JWTException) {
        try {
            log.warn("JWT Exception msg : {} | Wrapped Exception : {}", e.message, e.wrappingException?.message)
            response?.let {
                it.characterEncoding = "UTF-8"
                it.status = HttpStatus.UNAUTHORIZED.value() //로그인 정보가 잘못됐으니 UNAUTHROIZED
                it.writer.write(objectMapper.writeValueAsString(Response.ofFailure(e.message, null)))
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }


    private fun resolveTokenFromHeader(request: HttpServletRequest): String? {
        val bearerToken = request.getHeader("Authorization")
        return if (!bearerToken.isNullOrBlank() && bearerToken.length >= 7 && bearerToken.startsWith("Bearer")) //Bearer A23edfFF 이렇게 헤더에 옴
            return bearerToken.substring(7) //"Bearer " 잘라내고 나머지 토큰 가져옴
        else
            null
    }

}