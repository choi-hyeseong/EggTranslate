package com.example.demo.member.user.dto

import com.example.demo.member.user.entity.User

//최소한의 정보만 반환하는 DTO
class UserInfoDTO(
    val name : String,
    val phone : String,
    val email : String,
    val languages : MutableList<String>
) {
    constructor(user: User) : this(user.name, user.phone, user.email, user.language)

}