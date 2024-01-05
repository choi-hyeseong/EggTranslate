package com.example.demo.user.parent.child.dto

import com.example.demo.user.parent.child.entity.Child

class ChildRequestDto(
    val name: String,
    val phone: String,
    val school: String,
    val grade: Int,
    val className: String
) {
    fun toEntity() : Child = Child(name, phone, school, grade, className)
}