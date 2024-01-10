package com.example.demo.user.teacher.service

import com.example.demo.user.basic.dto.UserDto
import com.example.demo.user.basic.exception.UserNotFoundException
import com.example.demo.user.teacher.dto.TeacherDTO
import com.example.demo.user.teacher.repository.TeacherRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TeacherService(private val teacherRepository: TeacherRepository) {

    @Transactional
    fun signUp(teacherDTO: TeacherDTO): Long {
        return teacherRepository.save(teacherDTO.toEntity()).id
    }

    @Transactional(readOnly = true)
    fun existTeacherByUserId(id : Long) : Boolean = teacherRepository.existsByUserId(id)

    @Transactional(readOnly = true)
    fun findTeacherByUserId(id: Long): TeacherDTO =
        TeacherDTO(teacherRepository
        .findByUserId(id)
        .orElseThrow { UserNotFoundException(id, "할당되지 않은 유저 id입니다.") }
    )

    @Transactional(readOnly = true)
    fun findTeacherById(id : Long) : TeacherDTO =
        TeacherDTO(teacherRepository
            .findById(id)
            .orElseThrow { UserNotFoundException(id, "할당되지 않은 유저 id입니다.") }
        )

    @Transactional
    suspend fun updateProfile(id : Long, teacherDTO: TeacherDTO) {
        val existingUser = teacherRepository.findById(id).orElseThrow{
            UserNotFoundException(id, "일치하는 사용자가 없습니다")
        }
        existingUser.school = teacherDTO.school
        existingUser.grade = teacherDTO.grade
        existingUser.className = teacherDTO.className
        existingUser.course = teacherDTO.course
        existingUser.address = teacherDTO.address

        teacherRepository.save(existingUser)
    }
}