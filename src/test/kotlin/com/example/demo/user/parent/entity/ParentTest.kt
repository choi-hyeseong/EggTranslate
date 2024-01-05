package com.example.demo.user.parent.entity

import com.example.demo.user.basic.type.Gender
import com.example.demo.user.parent.child.dto.ChildRequestDto
import com.example.demo.user.parent.child.entity.Child
import com.example.demo.user.parent.child.repository.ChildRepository
import com.example.demo.user.parent.dto.ParentDTO
import com.example.demo.user.parent.repository.ParentRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class ParentTest(@Autowired private val parentRepository: ParentRepository, @Autowired private val childRepository: ChildRepository) {

    @Test
    fun TEST_SAVE_PARENT() {
        val children = mutableListOf(ChildRequestDto(
            "호식이",
            "전화",
            "학교",
            2,
            "병아리",
            Gender.MAN
        ), ChildRequestDto(
            "두마리",
            "전화",
            "학교",
            2,
            "돌고래",
            Gender.MAN
        ))
        val dto = ParentDTO(
            "${System.currentTimeMillis()}",
            "PASSWORD",
            "NAME",
            "PHONE",
            "EMAIL",
            mutableListOf(),
            children
            )
        val response = parentRepository.save(dto.toEntity())
        assertEquals(2, response.children.size)
        assertEquals("NAME", response.name)
        parentRepository.delete(response)
    }

    @Test
    fun TEST_LOAD_PARENT() {
        val children = mutableListOf(ChildRequestDto(
            "호식이",
            "전화",
            "학교",
            2,
            "병아리",
            Gender.MAN
        ), ChildRequestDto(
            "두마리",
            "전화",
            "학교",
            2,
            "돌고래",
            Gender.MAN
        ))
        val dto = ParentDTO(
            "${System.currentTimeMillis()}",
            "PASSWORD",
            "NAME",
            "PHONE",
            "EMAIL",
            mutableListOf(),
            children
        )
        val response = parentRepository.save(dto.toEntity())
        val load = parentRepository.findById(response.id).get()
        val firstId = load.children[0].id
        val secondId = load.children[1].id
        assertEquals(2, load.children.size)
        assertEquals("NAME", load.name)
        assertEquals("호식이", load.children[0].name)
        assertEquals("두마리", load.children[1].name)
        assertEquals(Gender.MAN, load.children[0].gender)
        parentRepository.delete(load)
        //cascade로 지워져야됨.
        assertFalse(childRepository.existsById(firstId))
        assertFalse(childRepository.existsById(secondId))
    }

    @Test
    fun TEST_LOAD_EMPTY_CHILD() {
        val children = mutableListOf<ChildRequestDto>()
        val dto = ParentDTO(
            "${System.currentTimeMillis()}",
            "PASSWORD",
            "NAME",
            "PHONE",
            "EMAIL",
            mutableListOf(),
            children
        )
        val response = parentRepository.save(dto.toEntity())
        val load = parentRepository.findById(response.id).get()
        assertTrue(load.children != null)
        assertEquals(0, load.children.size)
        parentRepository.delete(response)
    }
}