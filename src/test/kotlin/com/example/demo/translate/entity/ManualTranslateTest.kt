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
        null,
        userName = "테스트",
        password = "PASS",
        name = "테스트",
        phone = "010",
        email = null,
        languages = mutableListOf("한글", "영어"),
        userType = UserType.TEACHER
    ).toEntity()
    //save시 persist 되며 자동으로 user객체도 db랑 같이 매핑됨.

    @Test
    @Transactional
    fun TEST_SAVE_MANUAL() {
        val saveUser = UserDto(userRepository.save(user))

        val fileDto = FileDTO(null, "ORIGIN", "SAVE", saveUser, "PATH")
        val saveFile = fileRepository.save(fileDto.toEntity(user))

        val translateFileDTO = TranslateFileDTO(null, FileDTO(saveFile), "ORIGIN", "TRANSLATE", "FROM", "TO")
        val saveTranslateFile = translateFileRepository.save(translateFileDTO.toEntity(saveFile))

        val translateDTO = ManualTranslateDTO(null, TranslateFileDTO(saveTranslateFile), "TRANSLATED_CONTENT")
        val response : ManualTranslate = assertDoesNotThrow{ manualTranslateRepository.save(ManualTranslate(null, saveTranslateFile, translateDTO.content)) }
        assertNotEquals(-1, response.id)

    }

    @Test
    @Transactional
    fun TEST_LOAD_MANUAL() {
        val saveUser = userRepository.save(user)

        val fileDto = FileDTO(null, "ORIGIN", "SAVE", UserDto(saveUser), "PATH")
        val saveFile = fileRepository.save(fileDto.toEntity(user))

        val translateFileDTO = TranslateFileDTO(null, FileDTO(saveFile), "ORIGIN", "TRANSLATE", "FROM", "TO")
        val saveTranslateFile = translateFileRepository.save(translateFileDTO.toEntity(saveFile))

        val translateDTO = ManualTranslateDTO(null, TranslateFileDTO(saveTranslateFile), "TRANSLATED_CONTENT")
        val response : ManualTranslate = assertDoesNotThrow{ manualTranslateRepository.save(ManualTranslate(null, saveTranslateFile, translateDTO.content)) }
        assertNotEquals(-1, response.id)

        val load = assertDoesNotThrow { manualTranslateRepository.findById(response.id!!).get() }
        assertEquals("SAVE", load.translateFile.file.saveName)
        assertEquals("TRANSLATED_CONTENT", load.translateContent)

    }

}