package com.example.demo.auth.jwt.provider

import com.example.demo.member.user.dto.UserDto
import com.example.demo.member.user.type.UserType
import io.jsonwebtoken.ExpiredJwtException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.Duration

@SpringBootTest
class JWTTokenProviderTest(@Autowired private val jwtTokenProvider: JWTTokenProvider) {

    val userDto = UserDto(1, "ID", "PASS", "", "", "EMAIL", mutableListOf(), UserType.ADMIN)

    @Test
    fun TEST_EXPIRED_TOKEN() {
        val token = jwtTokenProvider.generateToken(userDto,Duration.ofSeconds(1) ,Duration.ZERO)
        Thread.sleep(1500)
        assertThrows(ExpiredJwtException::class.java) { jwtTokenProvider.parseClaims(token.accessToken) }
    }

    @Test
    fun TEST_NOT_EXPIRED_TOKEN() {
        val token = jwtTokenProvider.generateToken(userDto,Duration.ofSeconds(100) ,Duration.ZERO)
        Thread.sleep(1000)
        assertDoesNotThrow { jwtTokenProvider.parseClaims(token.accessToken) }
    }

    @Test
    fun TEST_LOAD_CLAIMS() {
        val token = jwtTokenProvider.generateToken(userDto, Duration.ofSeconds(100), Duration.ZERO)
        Thread.sleep(1000)
        val userClaim = assertDoesNotThrow { jwtTokenProvider.parseClaims(token.accessToken) }
        assertEquals(userDto.userName, userClaim.userName)
        assertEquals(userDto.id, userClaim.id)
        assertEquals(userDto.email, userClaim.email)
    }
}