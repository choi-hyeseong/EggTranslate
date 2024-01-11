package com.example.demo.translate.entity

import com.example.demo.file.dto.FileDTO
import com.example.demo.file.repository.FileRepository
import com.example.demo.translate.manual.dto.ManualTranslateDTO
import com.example.demo.translate.auto.dto.TranslateFileDTO
import com.example.demo.translate.manual.entity.ManualTranslate
import com.example.demo.translate.manual.repository.ManualTranslateRepository
import com.example.demo.translate.auto.repository.TranslateFileRepository
import com.example.demo.user.basic.dto.UserDto
import com.example.demo.user.basic.repository.UserRepository
import com.example.demo.user.basic.type.UserType
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
class ManualTranslateTest {

    @Autowired
    lateinit var manualTranslateRepository: ManualTranslateRepository

    @Autowired
    lateinit var fileRepository: FileRepository

    @Autowired
    lateinit var translateFileRepository: TranslateFileRepository

    @Autowired
    lateinit var userRepository: UserRepository

    val user = UserDto(
        userName = "테스트",
        password = "PASS",
        name = "테스트",
        phone = "010",
        email = null,
        languages = mutableListOf("한글", "영어"),
        userType = UserType.TEACHER
    ).toEntity()

    @Test
    @Transactional
    fun TEST_SAVE_MANUAL() {
        val saveUser = UserDto(userRepository.save(user))

        val fileDto = FileDTO(-1, "ORIGIN", "SAVE", saveUser, "PATH")
        val saveFile = FileDTO(fileRepository.save(fileDto.toEntity()))

        val translateFileDTO = TranslateFileDTO(-1, saveFile, "ORIGIN", "TRANSLATE", "FROM", "TO")
        val saveTranslateFile = TranslateFileDTO(translateFileRepository.save(translateFileDTO.toEntity()))

        val translateDTO = ManualTranslateDTO(-1, saveTranslateFile, "TRANSLATED_CONTENT")
        val response : ManualTranslate = assertDoesNotThrow{ manualTranslateRepository.save(translateDTO.toEntity()) }
        assertNotEquals(-1, response.id)

    }

    @Test
    @Transactional
    fun TEST_LOAD_MANUAL() {
        val saveUser = UserDto(userRepository.save(user))

        val fileDto = FileDTO(-1, "ORIGIN", "SAVE", saveUser, "PATH")
        val saveFile = FileDTO(fileRepository.save(fileDto.toEntity()))

        val translateFileDTO = TranslateFileDTO(-1, saveFile, "ORIGIN", "TRANSLATE", "FROM", "TO")
        val saveTranslateFile = TranslateFileDTO(translateFileRepository.save(translateFileDTO.toEntity()))

        val translateDTO = ManualTranslateDTO(-1, saveTranslateFile, "TRANSLATED_CONTENT")
        val response : ManualTranslate = assertDoesNotThrow{ manualTranslateRepository.save(translateDTO.toEntity()) }
        assertNotEquals(-1, response.id)

        val load = assertDoesNotThrow { manualTranslateRepository.findById(response.id).get() }
        assertEquals("SAVE", load.translateFile.file.saveName)
        assertEquals("TRANSLATED_CONTENT", load.translateContent)

    }

}