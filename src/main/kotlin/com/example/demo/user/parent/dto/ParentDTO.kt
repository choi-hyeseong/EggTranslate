package com.example.demo.user.parent.dto

import com.example.demo.user.basic.dto.UserDto
import com.example.demo.user.parent.child.dto.ChildRequestDto
import com.example.demo.user.parent.entity.Parent

class ParentDTO(
    var children : MutableList<ChildRequestDto>,
    val user : UserDto

) {

    constructor(parent: Parent) : this(parent.children.map { ChildRequestDto(it) }.toMutableList(), UserDto(parent.user))
    fun toEntity() : Parent = Parent(children.map { it.toEntity(childDTO.get(i).name, childDTO.get(i).phone, childDTO.get(i).school, childDTO.get(i).grade, childDTO.get(i).className, childDTO.get(i).gender) }.toMutableList(), user.toEntity(userDTO.id, userDTO.name, userDTO.password, userDTO.phone, userDTO.email, userDTO.languages, userDTO.userType))

}