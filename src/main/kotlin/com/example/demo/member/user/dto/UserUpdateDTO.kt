package com.example.demo.member.user.dto


class UserUpdateDTO (
    val password : String?,
    val name : String?,
    val phone : String?,
    val email : String?,
    val languages : MutableList<String>?,
)