package com.example.demo.user.basic.dto

import com.example.demo.user.basic.entity.User
import com.example.demo.user.basic.type.UserType
import com.fasterxml.jackson.annotation.JsonIgnore

class UserDto(
    var id : Long?,
    @JsonIgnore val userName : String,
    @JsonIgnore val password : String,
    val name : String,
    val phone : String,
    val email : String?,
    val languages : MutableList<String>,
    val userType: UserType
) {
    constructor(user: User) : this(user.id, user.username, user.password, user.name, user.phone, user.email, user.language, user.userType)
    fun toEntity() : User = User(id, userName, password, name, phone, email, languages, userType)

    fun toInfoDTO() : UserInfoDTO = UserInfoDTO(name, phone, email, languages)

    fun toResponseDTO() : UserResponseDTO = UserResponseDTO(id, userName, name, phone, email, languages, userType)

}