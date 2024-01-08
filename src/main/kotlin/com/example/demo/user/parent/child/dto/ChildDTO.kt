package com.example.demo.user.parent.child.dto

import com.example.demo.user.basic.dto.UserDto
import com.example.demo.user.parent.child.type.Gender
import com.example.demo.user.parent.child.entity.Child
import com.example.demo.user.parent.dto.ParentDTO

class ChildRequestDto(
    val name: String,
    val phone: String,
    val school: String,
    val grade: Int,
    val className: String,
    val gender: Gender
) {
    constructor(child: Child) : this(child.name, child.phone, child.school, child.grade, child.className, child.gender)
    fun toEntity() : Child = Child(name, phone, school, grade, className, gender)
}