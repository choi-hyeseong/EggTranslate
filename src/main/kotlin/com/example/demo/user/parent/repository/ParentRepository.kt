package com.example.demo.user.parent.repository

import com.example.demo.user.parent.entity.Parent
import org.springframework.data.jpa.repository.JpaRepository

interface ParentRepository : JpaRepository<Parent, Long>