package com.example.demo.user.parent.dto

import com.example.demo.user.basic.dto.UserDto
import com.example.demo.user.parent.child.dto.ChildDTO

//다른 타입 - 부모로 업데이트 하는 DTO
class ParentConvertDTO (
    val children : MutableList<ChildDTO>
) {
    fun toParentDTO(userDto: UserDto) : ParentDTO = ParentDTO(null, children, userDto)
}