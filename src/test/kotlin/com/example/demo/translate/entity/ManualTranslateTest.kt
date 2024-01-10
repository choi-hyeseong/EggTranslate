package com.example.demo.translate.entity

import com.example.demo.translate.dto.AutoTranslateDTO
import com.example.demo.translate.dto.ManualRequestDTO
import com.example.demo.translate.dto.ManualTranslateDTO
import com.example.demo.translate.repository.AutoTranslateRepository
import com.example.demo.translate.repository.ManualRequestRepository
import com.example.demo.translate.repository.ManualTranslateRepository
import com.example.demo.translate.type.TranslateState
import com.example.demo.user.basic.dto.UserDto
import com.example.demo.user.basic.repository.UserRepository
import com.example.demo.user.basic.type.UserType
import com.example.demo.user.parent.child.dto.ChildRequestDto
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
class ManualTranslateTest {

    @Autowired
    lateinit var translationResultRepository: ManualTranslateRepository

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var translatorRepository: TranslatorRepository

    @Autowired
    lateinit var autoTranslateRepository: AutoTranslateRepository

    @Autowired
    lateinit var manualRequestRepository: ManualRequestRepository

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
                    ChildRequestDto(
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
        val saveTranslator = translatorRepository.save(translator.toEntity())

        val autoTrans = AutoTranslateDTO(-1, UserDto(parentUser), mutableListOf())
        val saveAuto = autoTranslateRepository.save(autoTrans.toEntity())

        val request = ManualRequestDTO(
            -1,
            UserDto(parentUser),
            TranslateState.REQUEST,
            TranslatorDTO(saveTranslator),
            AutoTranslateDTO(saveAuto),
            ChildRequestDto(parent.children[0])
        )
        val savedRequest = assertDoesNotThrow { manualRequestRepository.save(request.toEntity()) }

        val translateResponse = ManualTranslateDTO(-1, "번역결과", ManualRequestDTO(savedRequest))
        val response = assertDoesNotThrow { translationResultRepository.save(translateResponse.toEntity()) }
        assertDoesNotThrow {
            manualRequestRepository.save(response.manualRequest.apply {
                status = TranslateState.DONE
            })
        }
        assertNotEquals(-1, response.id)
        assertEquals(TranslateState.DONE, manualRequestRepository.findById(savedRequest.id).get().status)

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
                    ChildRequestDto(
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
        val saveTranslator = translatorRepository.save(translator.toEntity())

        val autoTrans = AutoTranslateDTO(-1, UserDto(parentUser), mutableListOf())
        val saveAuto = autoTranslateRepository.save(autoTrans.toEntity())

        val request = ManualRequestDTO(
            -1,
            UserDto(parentUser),
            TranslateState.REQUEST,
            TranslatorDTO(saveTranslator),
            AutoTranslateDTO(saveAuto),
            ChildRequestDto(parent.children[0])
        )
        val savedRequest = assertDoesNotThrow { manualRequestRepository.save(request.toEntity()) }

        val translateResponse = ManualTranslateDTO(-1, "번역결과", ManualRequestDTO(savedRequest))
        val response = translationResultRepository.save(translateResponse.toEntity())

        val savedResponse = translationResultRepository.findById(response.id).get()
        assertEquals("번역결과", savedResponse.translateContent)
        assertEquals("정보처리기사", savedResponse.manualRequest.translator.certificates[0])
        assertEquals("개나리", savedResponse.manualRequest.child?.className)
    }


}