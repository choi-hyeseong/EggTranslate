package com.example.demo.user.parent.dto

import com.example.demo.user.basic.dto.UserDto
import com.example.demo.user.parent.child.dto.ChildRequestDto
import com.example.demo.user.parent.entity.Parent

class ParentDTO(
    var children : List<ChildRequestDto>,
    val user : UserDto

) {

    constructor(parent: Parent) : this(parent.children.map { ChildRequestDto(it) }, UserDto(parent.user))
    fun toEntity() : Parent = Parent(children.map { it.toEntity() }, user.toEntity())
}