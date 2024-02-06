package com.example.demo.user.teacher.service

import com.example.demo.user.basic.data.DataFetcher
import com.example.demo.user.basic.data.DataUpdater
import com.example.demo.common.page.Pageable
import com.example.demo.user.basic.exception.UserNotFoundException
import com.example.demo.user.basic.service.UserService
import com.example.demo.user.teacher.dto.*
import com.example.demo.user.teacher.entity.Teacher
import com.example.demo.user.teacher.repository.TeacherRepository
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import kotlin.math.max

@Service
class TeacherService(
    private val teacherRepository: TeacherRepository,
    private val userService: UserService
) : DataFetcher<TeacherListItemDTO, TeacherResponseDTO>, DataUpdater<TeacherConvertDTO, TeacherUpdateDTO, TeacherDTO> {

    @Transactional
    override suspend fun convert(id: Long, convertDTO: TeacherConvertDTO): Long? {
        val userDto = userService.getUser(id)
        return createTeacher(convertDTO.toTeacherDTO(userDto))
    }

    @Transactional
    override suspend fun update(id: Long, updateDTO: TeacherUpdateDTO): TeacherDTO {
        val teacher = findTeacherEntityByUserId(id)
        userService.updateUser(id, updateDTO.user) //유저 업데이트
        teacher.update(updateDTO)
        return TeacherDTO(teacherRepository.save(teacher))
    }


    @Transactional
    override suspend fun getList(page: Int, amount: Int): Pageable<TeacherListItemDTO> {
        val pageTeacher = teacherRepository.findAll(PageRequest.of(page, amount))
        return Pageable(page, max(0, pageTeacher.totalPages - 1), pageTeacher.content.map { TeacherListItemDTO(it) })
    }

    @Transactional
    override suspend fun getDetail(id: Long): TeacherResponseDTO {
        return findTeacherByUserId(id).toResponseDTO()
    }


    @Transactional
    suspend fun createTeacher(teacherDTO: TeacherDTO): Long? {
        val user = userService.getUserEntity(teacherDTO.user.id!!)
        return teacherRepository.save(teacherDTO.toEntity(user)).id
    }

    /*
    * JPA Section
    */

    @Transactional(readOnly = true)
    suspend fun findTeacherByUserId(id: Long): TeacherDTO =
        TeacherDTO(teacherRepository
            .findByUserId(id)
            .orElseThrow { UserNotFoundException(id, "할당되지 않은 유저 id입니다.") }
        )

    @Transactional
    suspend fun findTeacherEntityByUserId(id: Long): Teacher =
        teacherRepository.findByUserId(id).orElseThrow { UserNotFoundException(id, "할당되지 않은 유저 id입니다.") }


    @Transactional
    suspend fun deleteByUserId(id: Long) {
        val teacher = findTeacherByUserId(id)
        teacherRepository.deleteById(teacher.id!!)
    }

}