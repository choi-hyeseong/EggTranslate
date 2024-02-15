package com.example.demo.member.parent.dto

import com.example.demo.member.parent.child.dto.ChildDTO

class ParentResponseDTO (
    val id : Long?,
    val userId : Long?,
    var children : MutableList<ChildDTO>,
)