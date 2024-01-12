package com.example.demo.user.teacher.service

import com.example.demo.profile.dto.TeacherEditDTO
import com.example.demo.user.basic.exception.UserNotFoundException
import com.example.demo.user.basic.service.UserService
import com.example.demo.user.teacher.dto.TeacherDTO
import com.example.demo.user.teacher.repository.TeacherRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TeacherService(private val teacherRepository: TeacherRepository,
    private val userService: UserService) {

    @Transactional
    suspend fun signUp(teacherDTO: TeacherDTO): Long? {
        val user = userService.getUserEntity(teacherDTO.user.id!!)
        return teacherRepository.save(teacherDTO.toEntity(user)).id
    }

    @Transactional(readOnly = true)
    suspend fun existTeacherByUserId(id : Long) : Boolean = teacherRepository.existsByUserId(id)

    @Transactional(readOnly = true)
    suspend fun findTeacherByUserId(id: Long): TeacherDTO =
        TeacherDTO(teacherRepository
        .findByUserId(id)
        .orElseThrow { UserNotFoundException(id, "할당되지 않은 유저 id입니다.") }
    )

    @Transactional(readOnly = true)
    suspend fun findTeacherById(id : Long) : TeacherDTO =
        TeacherDTO(teacherRepository
            .findById(id)
            .orElseThrow { UserNotFoundException(id, "할당되지 않은 유저 id입니다.") }
        )

    @Transactional
    suspend fun updateProfile(id : Long, teacherEditDTO: TeacherEditDTO) {
        val existingUser = teacherRepository.findByUserId(id).orElseThrow{
            UserNotFoundException(id, "일치하는 사용자가 없습니다")
        }
        existingUser.school = teacherEditDTO.school
        existingUser.grade = teacherEditDTO.grade
        existingUser.className = teacherEditDTO.className
        existingUser.course = teacherEditDTO.course
        existingUser.address = teacherEditDTO.address

        teacherRepository.save(existingUser)
    }
}