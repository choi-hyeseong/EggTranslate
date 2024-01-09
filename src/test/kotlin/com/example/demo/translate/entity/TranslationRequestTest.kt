package com.example.demo.translate.entity

import com.example.demo.translate.dto.AutoTranslateDTO
import com.example.demo.translate.dto.TranslationRequestDTO
import com.example.demo.translate.repository.AutoTranslateRepository
import com.example.demo.translate.repository.TranslationRequestRepository
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
class TranslationRequestTest {

    @Autowired
    lateinit var userRepository : UserRepository

    @Autowired
    lateinit var translatorRepository: TranslatorRepository

    @Autowired
    lateinit var autoTranslateRepository: AutoTranslateRepository

    @Autowired
    lateinit var translationRequestRepository: TranslationRequestRepository

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
    fun TEST_SAVE_REQEUST() {
        val savedUser = userRepository.save(user)
        val savedParentUser = userRepository.save(parent)
        val parent = ParentDTO(
            mutableListOf(
                ChildRequestDto(-1, "하늘", "010", "학교", 3, "개나리", Gender.MAN)
            ),
            UserDto(savedUser)
        )
        val savedParent = parentRepository.save(parent.toEntity())

        val translator = TranslatorDTO(-1, 3, TranslatorLevel.HIGH, UserDto(savedUser), mutableListOf("정보처리기사"), mutableListOf(TranslatorCategory.OTHER))
        val saveTranslator = translatorRepository.save(translator.toEntity())

        val autoTrans = AutoTranslateDTO(-1, UserDto(savedUser), "ORIGIN", "TRANS", "FROM", "TO", mutableListOf())
        val saveAuto = autoTranslateRepository.save(autoTrans.toEntity())

        val request = TranslationRequestDTO(-1, UserDto(savedParentUser), TranslateState.REQUEST, TranslatorDTO(saveTranslator), AutoTranslateDTO(saveAuto), ChildRequestDto(savedParent.children[0]))
        val savedRequest = assertDoesNotThrow { translationRequestRepository.save(request.toEntity()) }
        assertNotEquals(-1, savedRequest.id)

    }

    @Test
    @Transactional
    fun TEST_LOAD_REQEUST() {
        val savedUser = userRepository.save(user)
        val savedParentUser = userRepository.save(parent)
        val parent = ParentDTO(
            mutableListOf(
                ChildRequestDto(-1, "하늘", "010", "학교", 3, "개나리", Gender.MAN)
            ),
            UserDto(savedUser)
        )
        val savedParent = parentRepository.save(parent.toEntity())

        val translator = TranslatorDTO(-1, 3, TranslatorLevel.HIGH, UserDto(savedUser), mutableListOf("정보처리기사"), mutableListOf(TranslatorCategory.OTHER))
        val saveTranslator = translatorRepository.save(translator.toEntity())

        val autoTrans = AutoTranslateDTO(-1, UserDto(savedUser), "ORIGIN", "TRANS", "FROM", "TO", mutableListOf())
        val saveAuto = autoTranslateRepository.save(autoTrans.toEntity())

        val request = TranslationRequestDTO(-1, UserDto(savedParentUser), TranslateState.REQUEST, TranslatorDTO(saveTranslator), AutoTranslateDTO(saveAuto), ChildRequestDto(savedParent.children[0]))
        val response = translationRequestRepository.save(request.toEntity())

        val savedRequest = assertDoesNotThrow { translationRequestRepository.findById(response.id).get() }

        assertEquals("하늘", savedRequest.child?.name)
        assertEquals("정보처리기사", savedRequest.translator.certificates[0])
        assertEquals(TranslateState.REQUEST, savedRequest.status)
        assertEquals(UserType.PARENT, savedRequest.userType)

    }

    @Test
    @Transactional
    fun TEST_EMPTY_CHILD_REQEUST() {
        val savedUser = userRepository.save(user)
        val savedParentUser = userRepository.save(parent)
        val parent = ParentDTO(
            mutableListOf(),
            UserDto(savedUser)
        )
        val savedParent = parentRepository.save(parent.toEntity())

        val translator = TranslatorDTO(-1, 3, TranslatorLevel.HIGH, UserDto(savedUser), mutableListOf("정보처리기사"), mutableListOf(TranslatorCategory.OTHER))
        val saveTranslator = translatorRepository.save(translator.toEntity())

        val autoTrans = AutoTranslateDTO(-1, UserDto(savedUser), "ORIGIN", "TRANS", "FROM", "TO", mutableListOf())
        val saveAuto = autoTranslateRepository.save(autoTrans.toEntity())

        val request = TranslationRequestDTO(-1, UserDto(savedParentUser), TranslateState.REQUEST, TranslatorDTO(saveTranslator), AutoTranslateDTO(saveAuto), null)
        val response = translationRequestRepository.save(request.toEntity())

        val savedRequest = assertDoesNotThrow { translationRequestRepository.findById(response.id).get() }

        assertNull(savedRequest.child)
        assertEquals("정보처리기사", savedRequest.translator.certificates[0])
        assertEquals(TranslateState.REQUEST, savedRequest.status)
        assertEquals(UserType.PARENT, savedRequest.userType)

    }

    @Test
    @Transactional
    fun TEST_SET_DONE_REQEUST() {
        val savedUser = userRepository.save(user)
        val savedParentUser = userRepository.save(parent)
        val parent = ParentDTO(
            mutableListOf(),
            UserDto(savedUser)
        )
        val savedParent = parentRepository.save(parent.toEntity())

        val translator = TranslatorDTO(-1, 3, TranslatorLevel.HIGH, UserDto(savedUser), mutableListOf("정보처리기사"), mutableListOf(TranslatorCategory.OTHER))
        val saveTranslator = translatorRepository.save(translator.toEntity())

        val autoTrans = AutoTranslateDTO(-1, UserDto(savedUser), "ORIGIN", "TRANS", "FROM", "TO", mutableListOf())
        val saveAuto = autoTranslateRepository.save(autoTrans.toEntity())

        val request = TranslationRequestDTO(-1, UserDto(savedParentUser), TranslateState.REQUEST, TranslatorDTO(saveTranslator), AutoTranslateDTO(saveAuto), null)
        val response = translationRequestRepository.save(request.toEntity())

        val savedRequest = assertDoesNotThrow { translationRequestRepository.findById(response.id).get() }
        savedRequest.status = TranslateState.DONE
        translationRequestRepository.save(savedRequest)

        assertEquals(TranslateState.DONE, translationRequestRepository.findById(savedRequest.id).get().status)

    }
}