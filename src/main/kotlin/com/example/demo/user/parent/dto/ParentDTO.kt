package com.example.demo.user.parent.dto

import com.example.demo.user.basic.dto.UserDto
import com.example.demo.user.basic.entity.Member
import com.example.demo.user.parent.child.dto.ChildDTO
import com.example.demo.user.parent.child.entity.Child
import com.example.demo.user.parent.entity.Parent

class ParentDTO(
    val id : Long?,
    var children : MutableList<ChildDTO>,
    val user : UserDto

) {

    constructor(parent: Parent) : this(parent.id, parent.children.map { ChildDTO(it) }.toMutableList(), UserDto(parent.member))
    fun toEntity(member : Member, children : MutableList<Child>) : Parent = Parent(id, children, member)

}

