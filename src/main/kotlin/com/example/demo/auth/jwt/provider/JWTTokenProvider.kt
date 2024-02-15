package com.example.demo.auth.jwt.provider

import com.example.demo.auth.jwt.dto.TokenDTO
import com.example.demo.auth.jwt.exception.JWTException
import com.example.demo.auth.jwt.model.UserClaim
import com.example.demo.auth.jwt.parser.ClaimMapper
import com.example.demo.logger
import com.example.demo.member.user.dto.UserDto
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.security.Key
import java.time.Duration
import java.util.Date
import kotlin.jvm.Throws

@Component
class JWTTokenProvider(@Value("\${jwt.secret}") key: String, val claimMapper: ClaimMapper) {

    private lateinit var key: Key
    private val log = logger()

    init {
        // primary contructor - init순으로 진행.
        val byteKey = Decoders.BASE64.decode(key)
        this.key = Keys.hmacShaKeyFor(byteKey)
    }

    // userDTO 이용해서 접근하기. 로그인할때 누가 authentication 가져오고 그러니
    fun generateToken(userDto: UserDto, accessTokenExpire : Duration, refreshTokenExpire : Duration): TokenDTO {
        val now = Date().time

        val accessToken = Jwts.builder().apply {
            setClaims(claimMapper.createClaim(UserClaim(userDto.userName, userDto.id!!, userDto.email)))
            setExpiration(Date(now + accessTokenExpire.toMillis()))
            signWith(key, SignatureAlgorithm.HS256)
        }.compact()

        val refreshToken = Jwts.builder().apply {
            setExpiration(Date(now + refreshTokenExpire.toMillis()))
            signWith(key, SignatureAlgorithm.HS256)
        }.compact()

        return TokenDTO("Bearer", accessToken, refreshToken)
    }


    //accessToken으로부터 body 추출
    @Throws
    fun parseClaims(accessToken: String?): UserClaim {
        if (accessToken.isNullOrBlank())
            throw JWTException("토큰이 비어있습니다.", null)

        val claim = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).body
        return claimMapper.decodeClaim(claim) ?: throw JWTException("권한 정보가 없는 JWT 토큰입니다.", accessToken)
    }



}