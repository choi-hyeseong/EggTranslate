package com.example.demo.user.basic.dto

import com.example.demo.user.basic.entity.User

class UserDto(
    val id : Long = -1,
    val userName : String,
    val password : String,
    val name : String,
    val phone : String,
    val email : String?,
    val languages : List<String>
) {
    constructor(user: User) : this(user.id, user.username, user.password, user.name, user.phone, user.email, user.language)
    fun toEntity() : User = User(id, userName, password, name, phone, email, languages)

}