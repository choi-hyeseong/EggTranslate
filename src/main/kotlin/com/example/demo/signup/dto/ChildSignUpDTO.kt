package com.example.demo.signup.dto

import com.example.demo.member.parent.child.dto.ChildDTO
import com.example.demo.member.parent.child.type.Gender

class ChildSignUpDTO(
        val name: String,
        val phone: String,
        val school: String,
        val grade: Int,
        val className: String,
        val gender: Gender
) {
    fun toChildDTO() : ChildDTO = ChildDTO(null, name, phone, school, grade, className, gender)
}
