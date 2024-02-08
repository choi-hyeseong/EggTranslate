package com.example.demo.user.parent.entity

import com.example.demo.user.basic.dto.UserDto
import com.example.demo.user.basic.repository.UserRepository
import com.example.demo.user.basic.type.UserType
import com.example.demo.user.parent.child.type.Gender
import com.example.demo.user.parent.child.dto.ChildDTO
import com.example.demo.user.parent.child.entity.Child
import com.example.demo.user.parent.child.repository.ChildRepository
import com.example.demo.user.parent.dto.ParentDTO
import com.example.demo.user.parent.repository.ParentRepository
import jakarta.transaction.Transactional
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class ParentTest(@Autowired private val parentRepository: ParentRepository, @Autowired private val childRepository: ChildRepository, @Autowired private val userRepository: UserRepository) {

    val user = UserDto(
        null,
        userName = "테스트",
        password = "PASS",
        name = "테스트",
        phone = "010",
        email = "",
        languages = mutableListOf("한글", "영어"),
        userType = UserType.PARENT
    )

    @Transactional
    @Test
    fun TEST_SAVE_PARENT() {
        val user = userRepository.save(user.toEntity())

        val children = mutableListOf(ChildDTO(
            null,
            "호식이",
            "전화",
            "학교",
            2,
            "병아리",
            Gender.MAN,
        ), ChildDTO(
            null,
            "두마리",
            "전화",
            "학교",
            2,
            "돌고래",
            Gender.MAN,
        ))
        val dto = ParentDTO(
            null,
            children,
            UserDto(user)
        )

        val response = assertDoesNotThrow {  parentRepository.save(dto.toEntity(user, children.map {it.toEntity()
        }.toMutableList())) }
        assertEquals(2, response.children.size)
        assertNotNull(response.user)
        assertDoesNotThrow { response.children }
    }

    @Transactional
    @Test
    fun TEST_LOAD_PARENT() {
        val user = userRepository.save(user.toEntity())
        val children = mutableListOf(ChildDTO(
            null,
            "호식이",
            "전화",
            "학교",
            2,
            "병아리",
            Gender.MAN
        ), ChildDTO(
            null,
            "두마리",
            "전화",
            "학교",
            2,
            "돌고래",
            Gender.MAN
        ))
        val dto = ParentDTO(
            null,
            children,
            UserDto(user)
        )
        val response = parentRepository.save(dto.toEntity(user, children.map { it.toEntity() }.toMutableList()))
        val load = assertDoesNotThrow { parentRepository.findById(response.id!!).get() }
        val firstId = load.children[0].id
        val secondId = load.children[1].id
        assertEquals(2, load.children.size)
        assertEquals("호식이", load.children[0].name)
        assertEquals("두마리", load.children[1].name)
        assertEquals(Gender.MAN, load.children[0].gender)
        assertEquals(UserType.PARENT, user.userType)
        parentRepository.delete(load)
        //cascade로 지워져야됨.
        assertFalse(childRepository.existsById(firstId!!))
        assertFalse(childRepository.existsById(secondId!!))
    }

    @Transactional
    @Test
    fun TEST_LOAD_EMPTY_CHILD() {
        val user = userRepository.save(user.toEntity())
        val children = mutableListOf<Child>()
        val dto = ParentDTO(
            null,
            mutableListOf(),
            UserDto(user)
        )
        val response = parentRepository.save(dto.toEntity(user, children))
        val load = parentRepository.findById(response.id!!).get()
        assertNotNull(load.children)
        assertEquals(0, load.children.size)
    }






}