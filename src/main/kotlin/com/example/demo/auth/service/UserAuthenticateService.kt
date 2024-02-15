package com.example.demo.auth.service

import com.example.demo.auth.jwt.dto.TokenDTO
import com.example.demo.auth.jwt.exception.JWTException
import com.example.demo.auth.jwt.provider.JWTTokenProvider
import com.example.demo.member.user.dto.UserLoginDTO
import com.example.demo.member.user.service.UserService
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.stereotype.Service
import java.time.Duration
import kotlin.jvm.Throws

@Service
class UserAuthenticateService(private val userService: UserService, private val jwtTokenProvider: JWTTokenProvider) {

    //단위 ms
    private val ACCESS_EXPIRE: Long = 86400000
    private val REFRESH_EXPIRE: Long = 86400000 //TODO 얘는 더 길게 잡아서 재발급 되게

    //for login service
    fun login(userLoginDTO: UserLoginDTO) : TokenDTO {
        val userDto = userService.login(userLoginDTO)
        return jwtTokenProvider.generateToken(userDto, Duration.ofMillis(ACCESS_EXPIRE), Duration.ofMillis(REFRESH_EXPIRE))
    }

    //for filter
    @Throws(JWTException::class)
    fun authenticate(accessToken : String?) : Authentication {
        try {
            val claim = jwtTokenProvider.parseClaims(accessToken)
            val user = userService.findUserByEmail(claim.email)

            val authorities = listOf(SimpleGrantedAuthority(user.userType.name))
            val principal = User(claim.userName, "", authorities)
            return UsernamePasswordAuthenticationToken(principal, "", principal.authorities)
        }
        catch (e : Exception) {
            throw JWTException.wrap(e, accessToken) //JWT Exception이면 그대로, 아닌경우 JWTException으로 wrapping
        }
    }
}