package com.example.demo.translate.entity

import com.example.demo.translate.dto.AutoTranslateDTO
import com.example.demo.translate.repository.AutoTranslateRepository
import com.example.demo.user.basic.dto.UserDto
import com.example.demo.user.basic.repository.UserRepository
import com.example.demo.user.basic.type.UserType
import jakarta.transaction.Transactional
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class AutoTranslateTest {

    @Autowired
    private lateinit var userRepository : UserRepository

    @Autowired
    private lateinit var autoTranslateRepository: AutoTranslateRepository

    val user = UserDto(
        userName = "테스트",
        password = "PASS",
        name = "테스트",
        phone = "010",
        email = null,
        languages = mutableListOf("한글", "영어"),
        userType = UserType.PARENT
    ).toEntity(userDTO.id, userDTO.name, userDTO.password, userDTO.phone, userDTO.email, userDTO.languages, userDTO.userType)

    @Test
    @Transactional
    fun TEST_SAVE_AUTO_TRANSLATE() {
        val saveUser = userRepository.save(user)
        val autoTranslateDTO = AutoTranslateDTO(-1, UserDto(saveUser), "ORIGIN", "TRANS", "KO", "EN", mutableListOf())
        val response = org.junit.jupiter.api.assertDoesNotThrow { autoTranslateRepository.save(autoTranslateDTO.toEntity()) }
        assertNotEquals(-1, response.id)
    }

    @Test
    @Transactional
    fun TEST_LOAD_AUTO_TRANSLATE() {
        val saveUser = userRepository.save(user)
        val autoTranslateDTO = AutoTranslateDTO(-1, UserDto(saveUser), "ORIGIN", "TRANS", "KO", "EN", mutableListOf())
        autoTranslateRepository.save(autoTranslateDTO.toEntity())

        assertEquals("TRANS", autoTranslateRepository.findByUserId(saveUser.id).get().translate)

    }


}