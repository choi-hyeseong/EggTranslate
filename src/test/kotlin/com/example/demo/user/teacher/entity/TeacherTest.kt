package com.example.demo.user.teacher.entity

import com.example.demo.user.basic.dto.UserDto
import com.example.demo.user.basic.repository.UserRepository
import com.example.demo.user.basic.type.UserType
import com.example.demo.user.teacher.dto.TeacherDTO
import com.example.demo.user.teacher.repository.TeacherRepository
import jakarta.transaction.Transactional
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class TeacherTest(@Autowired private val teacherRepository: TeacherRepository, @Autowired private val userRepository: UserRepository) {

    val user = UserDto(
        userName = "테스트",
        password = "PASS",
        name = "테스트",
        phone = "010",
        email = null,
        languages = mutableListOf("한글", "영어"),
        userType = UserType.TEACHER
    ).toEntity(userDTO.id, userDTO.name, userDTO.password, userDTO.phone, userDTO.email, userDTO.languages, userDTO.userType)


    @Test
    @Transactional
    fun TEST_SAVE_TEACHER() {
        val saved = userRepository.save(user)
        val teacher = TeacherDTO(
            "대구학교",
            3,
            "병아리반",
            "수학",
            "대구광역시",
            UserDto(saved)
        )
        val response = assertDoesNotThrow { teacherRepository.save(teacher.toEntity()) }
        assertNotEquals(-1, response.id)

    }

    @Transactional
    @Test
    fun TEST_LOAD_TEACHER() {
        val saved = userRepository.save(user)
        val teacher = TeacherDTO(
            "대구학교",
            3,
            "병아리반",
            "수학",
            "대구광역시",
            UserDto(saved)
        )
        val response = assertDoesNotThrow { teacherRepository.save(teacher.toEntity()) }
        val responseTeacher = assertDoesNotThrow { teacherRepository.findById(response.id).get() }
        assertEquals("병아리반", responseTeacher.className)
        assertEquals(3, responseTeacher.grade)
        assertNotNull(responseTeacher.user)
        assertEquals(UserType.TEACHER, saved.userType)
        assertEquals("010",responseTeacher.user.phone)
    }

    @Transactional
    @Test
    fun TEST_NULL_FIELDS() {
        val saved = userRepository.save(user)
        val nullableTeacher = TeacherDTO(
            "대구학교",
            3,
            "병아리반",
            null,
            null,
            UserDto(saved)
        )
        assertDoesNotThrow {
          teacherRepository.save(nullableTeacher.toEntity())
        }
    }




}