package com.example.demo.user.teacher.repository

import com.example.demo.user.teacher.entity.Teacher
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TeacherRepository : JpaRepository<Teacher, Long>