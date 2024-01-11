package com.example.demo.translate.entity

import com.example.demo.translate.auto.dto.AutoTranslateDTO
import com.example.demo.translate.manual.dto.ManualResultDTO
import com.example.demo.translate.auto.dto.TranslateResultSaveDTO
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
        userName = "테스트",
        password = "PASS",
        name = "테스트",
        phone = "010",
        email = null,
        languages = mutableListOf("한글", "영어"),
        userType = UserType.TEACHER
    ).toEntity()

    val parent = UserDto(
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

        val parent = parentRepository.save(
            ParentDTO(
                -1,
                mutableListOf(
                    ChildDTO(
                        -1,
                        "자식",
                        "010",
                        "학교",
                        3,
                        "개나리",
                        Gender.MAN
                    )
                ),
                UserDto(parentUser)
            ).toEntity()
        )
        val translator = TranslatorDTO(
            -1, 3, TranslatorLevel.HIGH, UserDto(translatorUser), mutableListOf("정보처리기사"), mutableListOf(
                TranslatorCategory.OTHER
            )
        )
        val saveTranslator = TranslatorDTO(translatorRepository.save(translator.toEntity()))

        val autoTrans = AutoTranslateDTO(-1, UserDto(parentUser), mutableListOf())

        val request = TranslateResultSaveDTO(
            -1,
            UserDto(parentUser),
            autoTrans,
            ChildDTO(parent.children[0])
        )
        val savedRequest = assertDoesNotThrow { translateResultRepository.save(request.toEntity()) }

        val manualResultDTO = ManualResultDTO(-1,  saveTranslator, TranslateState.REQUEST, mutableListOf())
        savedRequest.manualResult = manualResultDTO.toEntity()
        translateResultRepository.save(savedRequest)



    }

    @Test
    @Transactional
    fun TEST_LOAD_RESULT() {
        val translatorUser = userRepository.save(user)
        val parentUser = userRepository.save(parent)

        val parent = parentRepository.save(
            ParentDTO(
                -1,
                mutableListOf(
                    ChildDTO(
                        -1,
                        "자식",
                        "010",
                        "학교",
                        3,
                        "개나리",
                        Gender.MAN
                    )
                ),
                UserDto(parentUser)
            ).toEntity()
        )
        val translator = TranslatorDTO(
            -1, 3, TranslatorLevel.HIGH, UserDto(translatorUser), mutableListOf("정보처리기사"), mutableListOf(
                TranslatorCategory.OTHER
            )
        )
        val saveTranslator = TranslatorDTO(translatorRepository.save(translator.toEntity()))

        val autoTrans = AutoTranslateDTO(-1, UserDto(parentUser), mutableListOf())

        val request = TranslateResultSaveDTO(
            -1,
            UserDto(parentUser),
            autoTrans,
            ChildDTO(parent.children[0])
        )
        val savedRequest = assertDoesNotThrow { translateResultRepository.save(request.toEntity()) }

        val manualResultDTO = ManualResultDTO(-1,  saveTranslator, TranslateState.REQUEST, mutableListOf())
        savedRequest.manualResult = manualResultDTO.toEntity()
        translateResultRepository.save(savedRequest)


        val response = assertDoesNotThrow {
            val result = translateResultRepository.findById(savedRequest.id).get()
            result.manualResult?.status = TranslateState.DONE
            translateResultRepository.save(result)
        }
        assertEquals("정보처리기사", response.manualResult!!.translator.certificates[0])
        assertEquals("개나리", response.child?.className)
        assertNotEquals(-1, response.id)
        assertEquals(TranslateState.DONE, translateResultRepository.findById(savedRequest.id).get().manualResult?.status)
        assertEquals(response.manualResult?.id, manualResultRepository.findById(response.manualResult!!.id).get().id)
    }




}