package com.example.demo.profile.dto

import com.example.demo.user.parent.child.dto.ChildRequestDto
import com.example.demo.user.parent.child.entity.Child
import com.example.demo.user.parent.child.type.Gender

class ChildEditDTO(
        val name: String,
        val phone: String,
        val school: String,
        val grade: Int,
        val className: String,
        val gender: Gender
) {
    fun toChildDTO() : ChildRequestDto = ChildRequestDto(-1, name, phone, school, grade, className, gender)
}