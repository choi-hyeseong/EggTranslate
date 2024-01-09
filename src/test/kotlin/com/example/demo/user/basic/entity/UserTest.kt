package com.example.demo.user.basic.entity

import com.example.demo.user.basic.dto.UserDto
import com.example.demo.user.basic.repository.UserRepository
import com.example.demo.user.basic.type.UserType
import jakarta.transaction.Transactional
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class UserTest {

    @Autowired
    private lateinit var userRepository: UserRepository

    @Test
    @Transactional
    fun TEST_SAVE_USER() {
        val user = UserDto(
            userName = "테스트",
            password = "PASS",
            name = "테스트",
            phone = "010",
            email = null,
            languages = mutableListOf("한글", "영어"),
            userType = UserType.PARENT
        ).toEntity()
        val response =  assertDoesNotThrow { userRepository.save(user) }
        assertNotEquals(-1, response.id)

    }


    @Test
    @Transactional
    fun TEST_LOAD_USER() {
        val user = UserDto(
            userName = "테스트",
            password = "PASS",
            name = "테스트",
            phone = "010",
            email = null,
            languages = mutableListOf("한글", "영어"),
            userType = UserType.PARENT
        ).toEntity()
        val response =  assertDoesNotThrow { userRepository.save(user) }
        assertNotEquals(-1, response.id)
        val findUser = assertDoesNotThrow { userRepository.findById(response.id).get() }
        assertEquals("테스트", findUser.name)
        assertEquals("010", findUser.phone)
        assertEquals("영어", findUser.language[1])
        assertEquals(UserType.PARENT, findUser.userType)
        assertNotNull(user.heartList)


    }
}