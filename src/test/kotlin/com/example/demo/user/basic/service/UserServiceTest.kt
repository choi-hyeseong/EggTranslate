package com.example.demo.user.basic.service

import com.example.demo.user.basic.dto.UserDto
import com.example.demo.user.basic.exception.UserNotFoundException
import com.example.demo.user.basic.type.UserType
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
class UserServiceTest {

    @Autowired
    lateinit var userService: UserService

    @Test
    @Transactional
    fun TEST_SAVE_USER() {
        val dto = UserDto(
            -1,
            "테스트",
            "1234",
            "이름",
            "폰",
            null,
            mutableListOf(),
            UserType.TEACHER
        )

        val response = userService.signUp(dto)

        assertNotEquals(-1, response)
        assertTrue(userService.existUser(response))
    }

    @Test
    @Transactional
    fun TEST_LOAD_USER() {
        val dto = UserDto(
            -1,
            "테스트",
            "1234",
            "이름",
            "폰",
            null,
            mutableListOf(),
            UserType.TEACHER
        )

        val response = userService.signUp(dto)

        val load = org.junit.jupiter.api.assertDoesNotThrow { userService.getUser(response) }
        assertTrue(userService.existUser(response))
        assertEquals(dto.name, load.name)
        assertEquals(dto.userType, load.userType)
    }

    @Test
    @Transactional
    fun TEST_USER_NOT_FOUND() {
        assertThrows(UserNotFoundException::class.java) { userService.getUser(-1) }
    }

    @Test
    @Transactional
    fun TEST_USER_DUPLICATE() {
        val dto = UserDto(
            -1,
            "테스트",
            "1234",
            "이름",
            "폰",
            null,
            mutableListOf(),
            UserType.TEACHER
        )
        val dto2 = UserDto(
            -1,
            "테스트",
            "1234",
            "이름",
            "폰",
            null,
            mutableListOf(),
            UserType.TEACHER
        )

        userService.signUp(dto)
        assertThrows(DataIntegrityViolationException::class.java) { userService.signUp(dto2) }
    }
}