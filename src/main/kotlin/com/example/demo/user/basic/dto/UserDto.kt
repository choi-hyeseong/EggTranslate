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
    fun toEntity(id: Long, name: String, password: String, phone: String, email: String?, languages: List<String>): User = User(this.id, userName, this.password, this.name, this.phone, this.email, this.languages)

}