package com.example.demo.user.translator.entity

import com.example.demo.user.translator.dto.TranslatorDTO
import com.example.demo.user.translator.repository.TranslatorRepository
import com.example.demo.user.translator.type.TranslatorCategory
import com.example.demo.user.translator.type.TranslatorLevel
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
    @Test
    fun TEST_SAVE_TRANSLATOR() {
        val translator = TranslatorDTO(
            "${System.currentTimeMillis()}",
            "12345678",
            "TEST",
            "PHONE",
            "EMAIL",
            mutableListOf("한국어", "영어"),
            3,
            TranslatorLevel.HIGH,
            mutableListOf("정보처리기사", "ITQ"),
            mutableListOf(TranslatorCategory.CULTURE, TranslatorCategory.EDUCATION)
        )
            val response = assertDoesNotThrow { translatorRepository.save(translator.toEntity()) }
            assertEquals(2, response.language.size)
            assertEquals("TEST", response.name)
            assertEquals(TranslatorLevel.HIGH, response.level)
            assertEquals(2, response.categories.size)
            assertEquals("EMAIL", response.email)

    }


    @Test
    fun TEST_LOAD_TRANSLATOR() {
        val translator = TranslatorDTO(
            "${System.currentTimeMillis()}",
            "12345678",
            "TEST",
            "PHONE",
            "EMAIL",
            mutableListOf("한국어", "영어"),
            3,
            TranslatorLevel.HIGH,
            mutableListOf("정보처리기사", "ITQ"),
            mutableListOf(TranslatorCategory.CULTURE, TranslatorCategory.EDUCATION)
        )


        runBlocking {
            val response = assertDoesNotThrow { translatorRepository.save(translator.toEntity()) }
            val load = translatorRepository.findById(response.id).get()
            assertNotNull(load) //response가 있는지
            load?.let {
                //기본 저장여부 측정
                assertEquals(response.id, load.id)
                assertEquals(2, load.language.size)
                assertEquals("TEST", load.name)

                assertEquals(2, load.categories.size)
                assertEquals("EMAIL", load.email)

                //ENUM 저장여부
                assertEquals(TranslatorLevel.HIGH, load.level)
                assertTrue(load.categories.contains(TranslatorCategory.CULTURE))
                assertTrue(load.categories.contains(TranslatorCategory.EDUCATION))
                //ENUM 직렬화, 역직력화 안됐는지 체크
                assertFalse(load.categories.contains(TranslatorCategory.OTHER))

                assertTrue(load.certificates.contains("정보처리기사"))
                assertTrue(load.certificates.contains("ITQ"))
            }

        }
    }


}