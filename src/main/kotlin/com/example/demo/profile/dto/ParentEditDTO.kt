package com.example.demo.profile.dto

import com.example.demo.signup.dto.ChildSignUpDTO
import com.example.demo.signup.dto.UserSignUpDTO

class ParentEditDTO(
        var children : MutableList<ChildEditDTO>,
        val user : UserEditDTO
) {
}