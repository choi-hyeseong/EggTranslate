package com.example.demo.user.teacher.service

import com.example.demo.common.page.Pageable
import com.example.demo.profile.dto.TeacherEditDTO
import com.example.demo.user.basic.exception.UserNotFoundException
import com.example.demo.user.basic.service.UserService
import com.example.demo.user.teacher.dto.TeacherDTO
import com.example.demo.user.teacher.dto.TeacherListItemDTO
import com.example.demo.user.teacher.dto.TeacherUpdateDTO
import com.example.demo.user.teacher.entity.Teacher
import com.example.demo.user.teacher.repository.TeacherRepository
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import kotlin.math.max

@Service
class TeacherService(private val teacherRepository: TeacherRepository,
    private val userService: UserService) {

    @Transactional
    suspend fun createTeacher(teacherDTO: TeacherDTO): Long? {
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

    @Transactional
    suspend fun findTeacherEntityByUserId(id : Long) : Teacher =
        teacherRepository.findByUserId(id).orElseThrow { UserNotFoundException(id, "할당되지 않은 유저 id입니다.")}

    @Transactional(readOnly = true)
    suspend fun findTeacherById(id : Long) : TeacherDTO =
        TeacherDTO(teacherRepository
            .findById(id)
            .orElseThrow { UserNotFoundException(id, "할당되지 않은 유저 id입니다.") }
        )

    @Transactional
    suspend fun updateTeacher(id : Long, teacherUpdateDTO: TeacherUpdateDTO) {
        val teacher = findTeacherEntityByUserId(id)
        userService.updateUser(id, teacherUpdateDTO.user) //유저 업데이트
        teacher.update(teacherUpdateDTO)
        teacherRepository.save(teacher)
    }

    @Transactional
    suspend fun deleteByUserId(id : Long) {
        val teacher = findTeacherByUserId(id)
        teacherRepository.deleteById(teacher.id!!)
    }

    @Transactional
    suspend fun findTeacherList(page : Int, amount : Int) : Pageable<TeacherListItemDTO> {
        val pageTeacher = teacherRepository.findAll(PageRequest.of(page, amount))
        return Pageable(page, max(0, pageTeacher.totalPages - 1), pageTeacher.content.map { TeacherListItemDTO(it) })
    }
}