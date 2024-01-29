package com.example.demo.translate.entity

import com.example.demo.translate.auto.dto.AutoTranslateDTO
import com.example.demo.translate.auto.dto.TranslateFileDTO
import com.example.demo.translate.auto.repository.AutoTranslateRepository
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
        null,
        userName = "테스트",
        password = "PASS",
        name = "테스트",
        phone = "010",
        email = null,
        languages = mutableListOf("한글", "영어"),
        userType = UserType.PARENT
    ).toEntity()

    @Test
    @Transactional
    fun TEST_SAVE_AUTO_TRANSLATE() {
        val saveUser = userRepository.save(user)
        val autoTranslateDTO = AutoTranslateDTO(null, mutableListOf())
        val response = org.junit.jupiter.api.assertDoesNotThrow { autoTranslateRepository.save(autoTranslateDTO.toEntity(mutableListOf())) }
        assertNotEquals(-1, response.id)
    }

    @Test
    @Transactional
    fun TEST_LOAD_AUTO_TRANSLATE() {
        val saveUser = userRepository.save(user)
        val autoTranslateDTO = AutoTranslateDTO(null,mutableListOf( ))
        val response = autoTranslateRepository.save(autoTranslateDTO.toEntity( mutableListOf()))

        assertEquals(response.id, autoTranslateRepository.findByUserId(saveUser.id!!).get().id)

    }




}