package com.example.demo.user.teacher.repository

import com.example.demo.user.teacher.entity.Teacher
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface TeacherRepository : JpaRepository<Teacher, Long> {

    fun findByUserId(id : Long) : Optional<Teacher>

    fun existsByUserId(id : Long) : Boolean
}