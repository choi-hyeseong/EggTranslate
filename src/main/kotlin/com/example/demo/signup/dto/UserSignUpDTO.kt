package com.example.demo.signup.dto

<<<<<<< HEAD
class UserSignUpDTO {
=======
import com.example.demo.user.basic.dto.UserDto
import com.example.demo.user.basic.entity.User
import com.example.demo.user.basic.type.UserType

class UserSignUpDTO(
    val userName: String,
    val password: String,
    val name: String,
    val phone: String,
    val email: String?,
    val languages: MutableList<String>,
    val userType: UserType
) {
    fun toUserDTO(): UserDto = UserDto(-1, userName, password, name, phone, email, languages, userType)
>>>>>>> b935c0f74224b0e6e4979396c72e57983ee28f69
}