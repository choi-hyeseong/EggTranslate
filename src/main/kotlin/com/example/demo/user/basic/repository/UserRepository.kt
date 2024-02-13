package com.example.demo.user.basic.repository

import com.example.demo.user.basic.entity.User
import com.example.demo.user.basic.type.UserType
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface UserRepository : JpaRepository<User, Long> {

    fun findByUsername(userName : String) : Optional<User>

    fun findByEmail(email : String) : Optional<User>

    fun existsByUsername(userName: String) : Boolean

    fun existsByEmail(email: String) : Boolean

    fun existsByUserType(userType: UserType) : Boolean

}