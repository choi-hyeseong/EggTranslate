package com.example.demo.user.basic.repository

import com.example.demo.user.basic.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long>