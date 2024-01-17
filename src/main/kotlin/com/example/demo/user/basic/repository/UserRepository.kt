package com.example.demo.user.basic.repository

import com.example.demo.user.basic.entity.Member
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface UserRepository : JpaRepository<Member, Long> {

    fun findByUsername(userName : String) : Optional<Member>

}