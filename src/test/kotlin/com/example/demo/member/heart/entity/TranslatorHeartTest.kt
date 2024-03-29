package com.example.demo.member.heart.entity

import com.example.demo.member.user.dto.UserDto
import com.example.demo.member.user.repository.UserRepository
import com.example.demo.member.user.type.UserType
import com.example.demo.member.heart.repository.TranslatorHeartRepository
import com.example.demo.member.translator.dto.TranslatorDTO
import com.example.demo.member.translator.repository.TranslatorRepository
import com.example.demo.member.translator.type.TranslatorLevel
import jakarta.transaction.Transactional
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class TranslatorHeartTest(
    @Autowired private val translatorHeartRepository: TranslatorHeartRepository,
    @Autowired private val userRepository: UserRepository,
    @Autowired private val translatorRepository: TranslatorRepository,
) {

    val user = UserDto(
        null,
        userName = "테스트",
        password = "PASS",
        name = "테스트",
        phone = "010",
        email = "EMAIL1",
        languages = mutableListOf("한글", "영어"),
        userType = UserType.PARENT
    )

    val user2 = UserDto(
        null,
        userName = "테스트2",
        password = "PASS",
        name = "테스트",
        phone = "010",
        email = "EMAIL2",
        languages = mutableListOf("한글", "영어"),
        userType = UserType.PARENT
    )

    val user3 = UserDto(
        null,
        userName = "테스트3",
        password = "PASS",
        name = "테스트",
        phone = "010",
        email = "EMAIL3 ",
        languages = mutableListOf("한글", "영어"),
        userType = UserType.PARENT
    )



    @Test
    @Transactional
    fun TEST_SAVE_HEART() {
        val translatorUser = userRepository.save(user.toEntity())
        val other = userRepository.save(user2.toEntity())
        val translatorDto = TranslatorDTO(
            null,
            3,
            TranslatorLevel.HIGH,
            UserDto(translatorUser),
            mutableListOf(),
            mutableListOf()
        )
        val translator = translatorRepository.save(translatorDto.toEntity(translatorUser))
        assertNotEquals(-1, translator.id)

        val heart = TranslatorHeart(null, other, translator)
        val saveHeart = translatorHeartRepository.save(heart)

        other.heartList.add(saveHeart) //other 기준으로 해야됨..
        translator.hearts.add(saveHeart) // 양쪽다 리스트 추가해야 작동함. M:N에선

        val response = translatorHeartRepository.save(saveHeart) //중간 테이블을 저장해야 전파됨..
        assertNotEquals(-1, response.id)
        assertFalse(userRepository.findById(other.id!!).get().heartList.isEmpty())
        assertFalse(translatorRepository.findById(translator.id!!).get().hearts.isEmpty())
    }


    @Test
    @Transactional
    fun TEST_LOAD_HEART() {
        TEST_SAVE_HEART()
        val user1 = assertDoesNotThrow { userRepository.findByUsername("테스트").get() }
        val teacherUser = assertDoesNotThrow { userRepository.findByUsername("테스트2").get() }
        assertTrue(teacherUser.heartList.isNotEmpty())
        assertEquals(TranslatorLevel.HIGH, teacherUser.heartList[0].translator?.level)
        val translator = assertDoesNotThrow { translatorRepository.findByUser(user1)!! }
        assertTrue(translator.hearts.isNotEmpty())
        assertEquals(teacherUser.heartList[0].id, translator.hearts[0].id)
    }

    @Test
    @Transactional
    fun TEST_COUNT_ALL_HEART() {
        TEST_SAVE_HEART()
        val saveUser3 = userRepository.save(user3.toEntity())
        val translator = translatorRepository.findByUser(userRepository.findByUsername("테스트").get())!!

        val heart = TranslatorHeart(null, saveUser3, translator)
        val response = translatorHeartRepository.save(heart)

        saveUser3.heartList.add(response)
        translator.hearts.add(response)

        userRepository.save(saveUser3)
        translatorRepository.save(translator)

        assertEquals(2, translatorHeartRepository.findAllByTranslatorId(translator.id!!).size)

    }




}