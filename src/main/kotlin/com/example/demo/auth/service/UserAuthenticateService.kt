package com.example.demo.auth.service

import com.example.demo.auth.jwt.dto.TokenDTO
import com.example.demo.auth.jwt.exception.JWTException
import com.example.demo.auth.jwt.provider.JWTTokenProvider
import com.example.demo.user.basic.dto.UserLoginDTO
import com.example.demo.user.basic.service.UserService
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.stereotype.Service

@Service
class UserAuthenticateService(private val userService: UserService, private val jwtTokenProvider: JWTTokenProvider) {

    //for login service
    fun login(userLoginDTO: UserLoginDTO) : TokenDTO {
        val userDto = userService.login(userLoginDTO)
        return jwtTokenProvider.generateToken(userDto)
    }

    //for filter
    fun authenticate(accessToken : String?) : Authentication {
        try {
            val claim = jwtTokenProvider.parseClaims(accessToken)
            val user = userService.findUserByEmail(claim.email)

            val authorities = listOf(SimpleGrantedAuthority(user.userType.name))
            val principal = User(claim.userName, "", authorities)
            return UsernamePasswordAuthenticationToken(principal, "", principal.authorities)
        }
        catch (e : Exception) {
            if (e is JWTException)
                throw e
            else
                throw JWTException("잘못된 유저 정보입니다. 다시 토큰을 발급받아주세요.", accessToken, e)
        }

    }
}