package com.example.demo.member.parent.dto

import com.example.demo.member.user.dto.UserUpdateDTO
import com.example.demo.member.parent.child.dto.ChildDTO

class ParentUpdateDTO (
    val user : UserUpdateDTO?,
    val children : List<ChildDTO>?
)