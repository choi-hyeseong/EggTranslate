package com.example.demo.user.teacher.entity

import com.example.demo.user.teacher.dto.TeacherDTO
import com.example.demo.user.teacher.repository.TeacherRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class TeacherTest(@Autowired private val teacherRepository: TeacherRepository) {

    val teacher = TeacherDTO(
        "TEST",
        "PASSWORD",
        "NAME",
        "PHONE",
        "EMAIL",
        mutableListOf(),
        "대구학교",
        3,
        "병아리반",
        "수학",
        "대구광역시"
    )

    @Test
    fun TEST_SAVE_TEACHER() {
        val response = teacherRepository.save(teacher.toEntity())
        assertEquals("대구학교", response.school)
        assertEquals(3, response.grade)
        assertEquals("병아리반", response.className)
        assertEquals("수학", response.course)
        assertEquals("대구광역시", response.address)
        teacherRepository.delete(response) //실제 환경에서 테스트하므로 지우기
    }


    @Test
    fun TEST_LOAD_TEACHER() {
        val request = teacherRepository.save(teacher.toEntity())
        val response = teacherRepository.findById(request.id).get()
        assertEquals("대구학교", response.school)
        assertEquals(3, response.grade)
        assertEquals("병아리반", response.className)
        assertEquals("수학", response.course)
        assertEquals("대구광역시", response.address)
        teacherRepository.delete(response) //실제 환경에서 테스트하므로 지우기
    }

    @Test
    fun TEST_NULL_FIELDS() {
        val nullableTeacher = TeacherDTO(
            teacher.userId,
            teacher.password,
            teacher.name,
            teacher.phone,
            teacher.email,
            teacher.language,
            teacher.school,
            teacher.grade,
            teacher.className,
            null,
            null
        )
        assertDoesNotThrow {
            val response = teacherRepository.save(nullableTeacher.toEntity())
            teacherRepository.delete(response)
        }
    }
}