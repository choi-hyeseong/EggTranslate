package com.example.demo.user.basic.dto

import com.example.demo.user.basic.entity.Member

//최소한의 정보만 반환하는 DTO
class UserInfoDTO(
    val name : String,
    val phone : String,
    val email : String?,
    val languages : MutableList<String>
) {
    constructor(member: Member) : this(member.name, member.phone, member.email, member.language)

}