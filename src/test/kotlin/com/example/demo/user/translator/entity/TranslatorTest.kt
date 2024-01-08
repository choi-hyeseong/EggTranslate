package com.example.demo.user.translator.entity

import com.example.demo.user.basic.dto.UserDto
import com.example.demo.user.basic.repository.UserRepository
import com.example.demo.user.translator.dto.TranslatorDTO
import com.example.demo.user.translator.repository.TranslatorRepository
import com.example.demo.user.translator.type.TranslatorCategory
import com.example.demo.user.translator.type.TranslatorLevel
import jakarta.transaction.Transactional
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class TranslatorTest {


    @Autowired
    private lateinit var translatorRepository: TranslatorRepository

    @Autowired
    private lateinit var userRepository: UserRepository

    val user = UserDto(
        userName = "테스트",
        password = "PASS",
        name = "테스트",
        phone = "010",
        email = null,
        languages = mutableListOf("한글", "영어")
    )
    @Transactional
    @Test
    fun TEST_SAVE_TRANSLATOR() {
        val user = userRepository.save(user.toEntity())
        assertNotEquals(-1, user.id) //save 잘된지
        val translator = TranslatorDTO(
            3,
            TranslatorLevel.HIGH,
            UserDto(user),
            mutableListOf("정보처리기사", "ITQ"),
            mutableListOf(TranslatorCategory.CULTURE, TranslatorCategory.EDUCATION)
        )
            val response = assertDoesNotThrow { translatorRepository.save(translator.toEntity()) }
            assertEquals(TranslatorLevel.HIGH, response.level)
            assertEquals(2, response.categories.size)

    }


    @Test
    @Transactional
    fun TEST_LOAD_TRANSLATOR() {
        val user = userRepository.save(user.toEntity())
        assertNotEquals(-1, user.id) //save 잘된지
        val translator = TranslatorDTO(
            3,
            TranslatorLevel.HIGH,
            UserDto(user),
            mutableListOf("정보처리기사", "ITQ"),
            mutableListOf(TranslatorCategory.CULTURE, TranslatorCategory.EDUCATION)
        )


        runBlocking {
            val response = assertDoesNotThrow { translatorRepository.save(translator.toEntity()) }
            val load = translatorRepository.findById(response.id).get()
            assertNotNull(load) //response가 있는지
            load.let {
                //기본 저장여부 측정
                //ENUM 저장여부
                assertEquals(TranslatorLevel.HIGH, load.level)
                assertTrue(load.categories.contains(TranslatorCategory.CULTURE))
                assertTrue(load.categories.contains(TranslatorCategory.EDUCATION))
                //ENUM 직렬화, 역직력화 안됐는지 체크
                assertFalse(load.categories.contains(TranslatorCategory.OTHER))

                assertTrue(load.certificates.contains("정보처리기사"))
                assertTrue(load.certificates.contains("ITQ"))

                assertEquals("한글", response.user.language[0]) //관계 잘 저장되었는지
                //1대1 단방향 매핑이라 User의 Translator 참조여부는 상관할 필요 X
            }



        }
    }


}