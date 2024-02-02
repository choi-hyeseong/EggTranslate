package com.example.demo.user.parent.dto

import com.example.demo.user.basic.dto.UserDto
import com.example.demo.user.parent.child.dto.ChildDTO

class ParentResponseDTO (
    val id : Long?,
    val userId : Long?,
    var children : MutableList<ChildDTO>,
)