package com.example.demo.member.parent.child.entity

import com.example.demo.member.user.dto.UserDto
import com.example.demo.member.user.repository.UserRepository
import com.example.demo.member.user.type.UserType
import com.example.demo.member.parent.child.dto.ChildDTO
import com.example.demo.member.parent.child.repository.ChildRepository
import com.example.demo.member.parent.child.type.Gender
import com.example.demo.member.parent.dto.ParentDTO
import com.example.demo.member.parent.repository.ParentRepository
import jakarta.transaction.Transactional
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class ChildTest(@Autowired private val userRepository: UserRepository, @Autowired private val childRepository: ChildRepository, @Autowired private val parentRepository: ParentRepository) {


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
    fun TEST_ADD_CHILD() {
        val user = userRepository.save(user.toEntity())
        val children = mutableListOf<ChildDTO>()
        val dto = ParentDTO(
            null,
            children,
            UserDto(user)
        )
        val response = parentRepository.save(dto.toEntity(user, mutableListOf()))
        val load = parentRepository.findById(response.id!!).get()
        load.children.add(
            ChildDTO(
                null,
            "호식이",
            "전화",
            "학교",
            2,
            "병아리",
            Gender.MAN
        ).toEntity())
        parentRepository.save(load)
        val saved = parentRepository.findById(response.id!!).get()
        assertTrue(childRepository.existsById(saved.children[0].id!!))
        assertEquals(1, saved.children.size)
    }

    @Transactional
    @Test
    fun TEST_REMOVE_CHILD() {
        val user = userRepository.save(user.toEntity())
        val children = mutableListOf<ChildDTO>()
        val dto = ParentDTO(
            null,
            children,
            UserDto(user)
        )
        val response = parentRepository.save(dto.toEntity(user, mutableListOf()))
        val load = parentRepository.findById(response.id!!).get()
        load.children.add(
            ChildDTO(
                null,
            "호식이",
            "전화",
            "학교",
            2,
            "병아리",
            Gender.MAN
        ).toEntity())
        parentRepository.save(load)
        val saved = parentRepository.findById(response.id!!).get()
        val child = saved.children[0].id

        assertTrue(childRepository.existsById(child!!))

        saved.children.removeLast()
        parentRepository.save(saved)

        assertFalse(childRepository.existsById(child))
    }

    @Transactional
    @Test
    fun TEST_EDIT_CHILD() {
        val user = userRepository.save(user.toEntity())
        val children = mutableListOf<ChildDTO>()
        val dto = ParentDTO(
            null,
            children,
            UserDto(user)
        )
        val response = parentRepository.save(dto.toEntity(user, mutableListOf()))
        val load = parentRepository.findById(response.id!!).get()
        load.children.add(
            ChildDTO(
                null,
            "호식이",
            "전화",
            "학교",
            2,
            "병아리",
            Gender.MAN
        ).toEntity())
        parentRepository.save(load)
        val saved = parentRepository.findById(response.id!!).get()
        val child = saved.children[0].id

        assertTrue(childRepository.existsById(child!!))

        saved.children[0].gender = Gender.WOMAN
        parentRepository.save(saved)

        assertEquals(Gender.WOMAN, childRepository.findById(saved.children[0].id!!).get().gender)
    }


}