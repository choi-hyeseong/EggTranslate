package com.example.demo.profile.dto

class ParentEditDTO(
        var children : MutableList<ChildEditDTO>,
        val user : UserEditDTO
) {
}