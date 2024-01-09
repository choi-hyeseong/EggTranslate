package com.example.demo.signup.dto

import com.example.demo.user.parent.child.dto.ChildRequestDto
import com.example.demo.user.parent.dto.ParentDTO

class ParentSignUpDTO(
    var children : MutableList<ChildRequestDto>,
    val user : UserSignUpDTO

) {
    fun toParentDTO() : ParentDTO = ParentDTO(-1, children, user.toUserDTO())

}