package com.example.demo.user.basic.dto

import com.example.demo.user.basic.entity.Member
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
    constructor(member: Member) : this(member.id, member.username, member.password, member.name, member.phone, member.email, member.language, member.userType)
    fun toEntity() : Member = Member(id, userName, password, name, phone, email, languages, userType)

    fun toInfoDTO() : UserInfoDTO = UserInfoDTO(name, phone, email, languages)

}