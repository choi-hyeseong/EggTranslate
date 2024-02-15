package com.example.demo.member.user.dto

import com.example.demo.member.user.entity.User
import com.example.demo.member.user.type.UserType

//비밀번호를 제외한 응답을 반환하는 DTO
class UserResponseDTO (
    var id : Long?,
    val userName : String,
    val name : String,
    val phone : String,
    val email : String,
    val languages : MutableList<String>,
    val userType: UserType
) {

    constructor(user : User) : this(user.id, user.username, user.name, user.phone, user.email, user.language, user.userType)
}