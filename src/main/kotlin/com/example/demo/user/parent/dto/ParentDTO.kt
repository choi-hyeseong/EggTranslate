package com.example.demo.user.parent.dto

import com.example.demo.user.parent.child.dto.ChildRequestDto
import com.example.demo.user.parent.entity.Parent

class ParentDTO(
    val userId : String,
    val password : String,
    val name : String,
    val phone : String,
    val email : String?,
    val languages : List<String>,
    val children : List<ChildRequestDto>

) {
    fun toEntity() : Parent = Parent(userId, password, name, phone, email, languages, children.map { it.toEntity() })
}