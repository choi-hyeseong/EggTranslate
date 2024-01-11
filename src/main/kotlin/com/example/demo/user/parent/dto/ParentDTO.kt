package com.example.demo.user.parent.dto

import com.example.demo.user.basic.dto.UserDto
import com.example.demo.user.parent.child.dto.ChildDTO
import com.example.demo.user.parent.entity.Parent

class ParentDTO(
    val id : Long = -1,
    var children : MutableList<ChildDTO>,
    val user : UserDto

) {

    constructor(parent: Parent) : this(parent.id, parent.children.map { ChildDTO(it) }.toMutableList(), UserDto(parent.user))
    fun toEntity() : Parent = Parent(id, children.map { it.toEntity() }.toMutableList(), user.toEntity())

}

