package com.example.demo.user.parent.dto

import com.example.demo.user.basic.dto.UserUpdateDTO
import com.example.demo.user.parent.child.dto.ChildDTO

class ParentUpdateDTO (
    val user : UserUpdateDTO,
    val children : List<ChildDTO>
)