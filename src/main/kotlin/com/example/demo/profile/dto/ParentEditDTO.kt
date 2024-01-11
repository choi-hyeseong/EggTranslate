package com.example.demo.profile.dto

import com.example.demo.signup.dto.ChildSignUpDTO
import com.example.demo.signup.dto.UserSignUpDTO
import com.example.demo.user.parent.child.dto.ChildRequestDto

class ParentEditDTO(
        var children : MutableList<ChildEditDTO>,
        val user : UserEditDTO
) {
}