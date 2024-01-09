package com.example.demo.file.entity

import com.example.demo.file.dto.FileDTO
import com.example.demo.file.dto.TranslateFileDTO
import com.example.demo.file.repository.FileRepository
import com.example.demo.file.repository.TranslateFileRepository
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
class TranslateFileTest {

    @Autowired lateinit var userRepository: UserRepository

    @Autowired lateinit var fileRepository: FileRepository

    @Autowired lateinit var autoTranslateRepository: AutoTranslateRepository

    @Autowired lateinit var translateFileRepository: TranslateFileRepository

    val user = UserDto(
        userName = "테스트",
        password = "PASS",
        name = "테스트",
        phone = "010",
        email = null,
        languages = mutableListOf("한글", "영어"),
        userType = UserType.PARENT
    )

    @Test
    @Transactional
    fun TEST_SAVE_FILE() {
        val saveUser = userRepository.save(user.toEntity())
        val file1 = FileDTO(-1, "FILE1", "", UserDto(saveUser), "")
        val saveFile1 = fileRepository.save(file1.toEntity())


        val autoTrans = AutoTranslateDTO(-1, UserDto(saveUser), "ORIGIN", "TRANSLATE", "FROM", "TO", mutableListOf())
        val saveAuto = autoTranslateRepository.save(autoTrans.toEntity())

        val translateFile = TranslateFileDTO(-1, FileDTO(saveFile1), AutoTranslateDTO(saveAuto))
        val saved = translateFileRepository.save(translateFile.toEntity())

        saveAuto.translateFiles.add(saved)
        autoTranslateRepository.save(saveAuto)

        assertNotEquals(-1, saved.id)
        assertFalse(autoTranslateRepository.findById(saveAuto.id).get().translateFiles.isEmpty())

    }

    @Test
    @Transactional
    fun TEST_LOAD_FIlES() {
        val saveUser = userRepository.save(user.toEntity())
        val file1 = FileDTO(-1, "FILE1", "", UserDto(saveUser), "")
        val file2 = FileDTO(-1, "FILE2", "", UserDto(saveUser), "")
        val saveFile1 = fileRepository.save(file1.toEntity())
        val saveFile2 = fileRepository.save(file2.toEntity())

        val autoTrans = AutoTranslateDTO(-1, UserDto(saveUser), "ORIGIN", "TRANSLATE", "FROM", "TO", mutableListOf())
        val saveAuto = autoTranslateRepository.save(autoTrans.toEntity())

        val translateFile = TranslateFileDTO(-1, FileDTO(saveFile1), AutoTranslateDTO(saveAuto))
        val saved = translateFileRepository.save(translateFile.toEntity())

        val translateFile2 = TranslateFileDTO(-1, FileDTO(saveFile2), AutoTranslateDTO(saveAuto))
        val save2 = translateFileRepository.save(translateFile2.toEntity())


        saveAuto.translateFiles.apply {
            add(saved)
            add(save2)
        }
        autoTranslateRepository.save(saveAuto)

        assertNotEquals(-1, saved.id)
        val result = translateFileRepository.findAllByAutoTranslateId(saveAuto.id)
        val resultAuto = autoTranslateRepository.findByUserId(saveUser.id).get()
        assertTrue(result.isNotEmpty())
        assertEquals(2, result.size)

        assertEquals("FILE1", resultAuto.translateFiles[0].file.originName)
        assertEquals("FILE2", resultAuto.translateFiles[1].file.originName)

    }



}