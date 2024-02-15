package com.example.demo.member.parent.repository

import com.example.demo.member.parent.entity.Parent
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface ParentRepository : JpaRepository<Parent, Long> {

    fun findByUserId(id : Long) : Optional<Parent>

    fun existsByUserId(userId : Long) : Boolean
}