package com.example.demo.user.basic.repository

import com.example.demo.user.basic.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface UserRepository : JpaRepository<User, Long> {

    fun findByUsername(userName : String) : Optional<User>

}