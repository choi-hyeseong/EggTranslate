package com.example.demo.translate.entity

import com.example.demo.file.dto.FileDTO
import com.example.demo.file.repository.FileRepository
import com.example.demo.translate.auto.dto.AutoTranslateDTO
import com.example.demo.translate.auto.dto.TranslateFileDTO
import com.example.demo.translate.auto.dto.TranslateResultDTO
import com.example.demo.translate.auto.repository.AutoTranslateRepository
import com.example.demo.translate.auto.repository.TranslateFileRepository
import com.example.demo.translate.auto.repository.TranslateResultRepository
import com.example.demo.translate.manual.type.TranslateState
import com.example.demo.user.basic.dto.UserDto
import com.example.demo.user.basic.repository.UserRepository
import com.example.demo.user.basic.type.UserType
import com.example.demo.user.parent.child.dto.ChildDTO
import com.example.demo.user.parent.child.type.Gender
import com.example.demo.user.parent.dto.ParentDTO
import com.example.demo.user.parent.repository.ParentRepository
import com.example.demo.user.translator.dto.TranslatorDTO
import com.example.demo.user.translator.repository.TranslatorRepository
import com.example.demo.user.translator.type.TranslatorCategory
import com.example.demo.user.translator.type.TranslatorLevel
import jakarta.transaction.Transactional
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class TranslateResultTest {


    @Autowired
    lateinit var userRepository : UserRepository

    @Autowired
    lateinit var translateResultRepository: TranslateResultRepository

    @Autowired
    lateinit var parentRepository: ParentRepository

    @Autowired
    lateinit var fileRepository: FileRepository



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

    val parent = UserDto(
        null,
        userName = "테스트1",
        password = "PASS",
        name = "부모님",
        phone = "010",
        email = null,
        languages = mutableListOf("한글", "영어"),
        userType = UserType.PARENT
    ).toEntity()


    @Test
    @Transactional
    fun TEST_SAVE_REQEUST() {
        val savedUser = userRepository.save(user)
        val savedParentUser = userRepository.save(parent)
        val child = mutableListOf(
            ChildDTO(null, "하늘", "010", "학교", 3, "개나리", Gender.MAN)
        )
        val parent = ParentDTO(
            null,
            child,
            UserDto(savedUser)
        )
        val savedParent = parentRepository.save(parent.toEntity(savedParentUser,child.map { it.toEntity() }.toMutableList() ))

        val autoTrans = AutoTranslateDTO(    null, UserDto(savedUser), mutableListOf())

        val request = TranslateResultDTO(null, UserDto(savedParentUser), autoTrans, ChildDTO(savedParent.children[0]), null)
        val savedRequest = assertDoesNotThrow { translateResultRepository.save(request.toEntity(savedParentUser, autoTrans.toEntity(savedParentUser, mutableListOf()), null)) }
        assertNotEquals(-1, savedRequest.id)

    }

    @Test
    @Transactional
    fun TEST_LOAD_REQEUST() {
        val savedUser = userRepository.save(user)
        val savedParentUser = userRepository.save(parent)
        val child = mutableListOf(
            ChildDTO(null, "하늘", "010", "학교", 3, "개나리", Gender.MAN)
        )
        val parent = ParentDTO(
            null,
            child,
            UserDto(savedUser)
        )
        val savedParent = parentRepository.save(parent.toEntity(savedParentUser,child.map { it.toEntity() }.toMutableList() ))


        val fileDto = FileDTO(null, "ORIGIN_NAME", "SAVE", parent.user, "PATH")
        val saveFile = FileDTO(fileRepository.save(fileDto.toEntity(savedParentUser)))

        val translateFileDTO = TranslateFileDTO(null, saveFile, "ORIGIN", "TRANSLATE", "FROM", "TO")
        val autoTrans = AutoTranslateDTO(null, UserDto(savedUser), mutableListOf(translateFileDTO))

        val request = TranslateResultDTO(null, UserDto(savedParentUser), autoTrans, ChildDTO(savedParent.children[0]), null)
        val savedRequest = assertDoesNotThrow { translateResultRepository.save(request.toEntity(savedParentUser, autoTrans.toEntity(savedParentUser, autoTrans.translateFile.map {
            val fileDto = it.file
            val loadFile = fileRepository.findById(fileDto.id!!).get()
            it.toEntity(loadFile)
        }.toMutableList()), null)) }


        val loadRequest = assertDoesNotThrow { translateResultRepository.findById(savedRequest.id!!).get() }
        assertNotEquals(-1, loadRequest.id)
        assertEquals("하늘", loadRequest.child?.name)
        assertEquals(UserType.PARENT, loadRequest.userType)
        assertEquals("TRANSLATE", loadRequest.autoTranslate.translateFiles[0].translate)
        assertEquals(saveFile.id, loadRequest.autoTranslate.translateFiles[0].file.id)

    }

    @Test
    @Transactional
    fun TEST_EMPTY_CHILD_REQEUST() {
        val savedUser = userRepository.save(user)
        val savedParentUser = userRepository.save(parent)

        val autoTrans = AutoTranslateDTO(null, UserDto(savedUser), mutableListOf())
        val autoEntity = autoTrans.toEntity(savedParentUser, mutableListOf())

        val request = TranslateResultDTO(null, UserDto(savedParentUser), autoTrans, null, null)
        val response = translateResultRepository.save(request.toEntity(savedParentUser, autoEntity, null))

        val savedRequest = assertDoesNotThrow { translateResultRepository.findById(response.id!!).get() }

        assertNull(savedRequest.child)
        assertEquals(UserType.PARENT, savedRequest.userType)
    }

}