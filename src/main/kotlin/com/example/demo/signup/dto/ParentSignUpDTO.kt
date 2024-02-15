package com.example.demo.signup.dto

import com.example.demo.member.user.type.UserType
import com.example.demo.member.parent.dto.ParentDTO

class ParentSignUpDTO(
    var children : MutableList<ChildSignUpDTO>,
    val user : UserSignUpDTO

) {
    fun toParentDTO() : ParentDTO = ParentDTO(null, children.map { it.toChildDTO() }.toMutableList(), user.toUserDTO(
        UserType.PARENT))

}