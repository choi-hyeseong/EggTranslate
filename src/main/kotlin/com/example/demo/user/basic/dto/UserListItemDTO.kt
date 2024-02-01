package com.example.demo.user.basic.dto

import com.example.demo.user.basic.entity.User
import com.example.demo.user.basic.type.UserType

//리스트 표현용 dto
class UserListItemDTO (
    var id : Long?,
    val userName : String,
    val name : String,
    val userType: UserType
) {

    constructor(user : User) : this(user.id, user.username, user.name, user.userType)
}