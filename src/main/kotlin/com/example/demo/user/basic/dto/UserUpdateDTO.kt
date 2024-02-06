package com.example.demo.user.basic.dto


class UserUpdateDTO (
    val password : String?,
    val name : String?,
    val phone : String?,
    val email : String?,
    val languages : MutableList<String>?,
)