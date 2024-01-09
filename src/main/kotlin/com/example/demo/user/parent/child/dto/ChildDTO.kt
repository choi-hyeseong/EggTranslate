package com.example.demo.user.parent.child.dto

import com.example.demo.user.parent.child.type.Gender
import com.example.demo.user.parent.child.entity.Child

class ChildRequestDto(
    val name: String,
    val phone: String,
    val school: String,
    val grade: Int,
    val className: String,
    val gender: Gender
) {
    constructor(child: Child) : this(child.name, child.phone, child.school, child.grade, child.className, child.gender)
    fun toEntity(name: String, phone: String, school: String, grade: Int, className: String, gender: Gender): Child = Child(this.name, this.phone, this.school, this.grade, this.className, this.gender)
}