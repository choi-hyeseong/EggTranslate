package com.example.demo.user.basic.dto

import com.example.demo.user.basic.entity.User
import com.example.demo.user.basic.type.UserType
import com.fasterxml.jackson.annotation.JsonIgnore

//최소한의 정보만 반환하는 DTO
class UserInfoDTO(
    val name : String,
    val phone : String,
    val email : String?,
    val languages : MutableList<String>
) {
    constructor(user: User) : this(user.name, user.phone, user.email, user.language)

}