package com.example.demo.user.parent.child.repository

import com.example.demo.user.parent.child.entity.Child
import org.jetbrains.annotations.TestOnly
import org.springframework.data.jpa.repository.JpaRepository

@TestOnly
interface ChildRepository : JpaRepository<Child, Long>