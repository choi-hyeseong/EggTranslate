package com.example.demo.translate.entity

import com.example.demo.translate.auto.dto.AutoTranslateDTO
import com.example.demo.translate.auto.dto.TranslateResultDTO
import com.example.demo.translate.manual.dto.ManualResultDTO
import com.example.demo.translate.auto.repository.AutoTranslateRepository
import com.example.demo.translate.auto.repository.TranslateResultRepository
import com.example.demo.translate.manual.repository.ManualResultRepository
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
class ManualResultTest {


    @Autowired
    lateinit var manualResultRepository: ManualResultRepository

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var translatorRepository: TranslatorRepository

    @Autowired
    lateinit var autoTranslateRepository: AutoTranslateRepository

    @Autowired
    lateinit var translateResultRepository: TranslateResultRepository

    @Autowired
    lateinit var parentRepository: ParentRepository


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
    fun TEST_SAVE_RESULT() {
        val translatorUser = userRepository.save(user)
        val parentUser = userRepository.save(parent)

        val child = mutableListOf(
            ChildDTO(
                null,
                "자식",
                "010",
                "학교",
                3,
                "개나리",
                Gender.MAN
            )
        )
        val parent = parentRepository.save(
            ParentDTO(
                null,
                child,
                UserDto(parentUser)
            ).toEntity(parentUser, child.map { it.toEntity() }.toMutableList())
        )
        val translator = TranslatorDTO(
            null, 3, TranslatorLevel.HIGH, UserDto(translatorUser), mutableListOf("정보처리기사"), mutableListOf(
                TranslatorCategory.OTHER
            )
        )
        val saveTranslator = translatorRepository.save(translator.toEntity(translatorUser))

        val autoTrans = AutoTranslateDTO(null, UserDto(parentUser), mutableListOf())

        val request = TranslateResultDTO(
            null,
            UserDto(parentUser),
            autoTrans,
            ChildDTO(parent.children[0]),
            null
        )
        val savedRequest = assertDoesNotThrow { translateResultRepository.save(request.toEntity(parentUser, autoTrans.toEntity(parentUser, mutableListOf()), null)) }

        val manualResultDTO = ManualResultDTO(null,  TranslatorDTO(saveTranslator), TranslateState.REQUEST, mutableListOf())
        savedRequest.manualResult = manualResultDTO.toEntity(saveTranslator, mutableListOf())
        translateResultRepository.save(savedRequest)



    }

    @Test
    @Transactional
    fun TEST_LOAD_RESULT() {
        val translatorUser = userRepository.save(user)
        val parentUser = userRepository.save(parent)

        val child = mutableListOf(
            ChildDTO(
                null,
                "자식",
                "010",
                "학교",
                3,
                "개나리",
                Gender.MAN
            )
        )
        val parent = parentRepository.save(
            ParentDTO(
                null,
                child,
                UserDto(parentUser)
            ).toEntity(parentUser, child.map { it.toEntity() }.toMutableList())
        )
        val translator = TranslatorDTO(
            null, 3, TranslatorLevel.HIGH, UserDto(translatorUser), mutableListOf("정보처리기사"), mutableListOf(
                TranslatorCategory.OTHER
            )
        )
        val saveTranslator = translatorRepository.save(translator.toEntity(translatorUser))

        val autoTrans = AutoTranslateDTO(null, UserDto(parentUser), mutableListOf())

        val request = TranslateResultDTO(
            null,
            UserDto(parentUser),
            autoTrans,
            ChildDTO(parent.children[0]),
            null
        )
        val savedRequest = assertDoesNotThrow { translateResultRepository.save(request.toEntity(parentUser, autoTrans.toEntity(parentUser, mutableListOf()), null)) }

        val manualResultDTO = ManualResultDTO(null,  TranslatorDTO(saveTranslator), TranslateState.REQUEST, mutableListOf())
        savedRequest.manualResult = manualResultDTO.toEntity(saveTranslator, mutableListOf())
        translateResultRepository.save(savedRequest)



        val response = assertDoesNotThrow {
            val result = translateResultRepository.findById(savedRequest.id!!).get()
            result.manualResult?.status = TranslateState.DONE
            translateResultRepository.save(result)
        }
        assertEquals("정보처리기사", response.manualResult!!.translator.certificates[0])
        assertEquals("개나리", response.child?.className)
        assertNotEquals(-1, response.id)
        assertEquals(TranslateState.DONE, translateResultRepository.findById(savedRequest.id!!).get().manualResult?.status)
        assertEquals(response.manualResult?.id, manualResultRepository.findById(response.manualResult!!.id!!).get().id)
    }






}