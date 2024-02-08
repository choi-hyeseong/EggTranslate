package com.example.demo.signup.validation

import com.example.demo.admin.dto.AdminSignUpDTO
import com.example.demo.signup.dto.ParentSignUpDTO
import com.example.demo.signup.dto.TeacherSignUpDTO
import com.example.demo.signup.dto.TranslatorSignUpDTO
import com.example.demo.signup.exception.DuplicatedException
import com.example.demo.user.basic.dto.UserDto
import com.example.demo.user.basic.service.UserService
import com.example.demo.user.basic.type.UserType
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import kotlinx.coroutines.*
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Component

@Component
@RequiredArgsConstructor
class SignUpValidator(private val userService: UserService) : ConstraintValidator<SignUpValid, Any> {

    /* 비밀번호도 형식이 필요하다면 사용하면 되겠음.*/
//    override fun isValid(userDto: UserDto, context: ConstraintValidatorContext) : Boolean {
//        return isUsernameValid(userDto.username) && isPasswordValid(userDto.password)
//    }

    override fun isValid(value: Any, context: ConstraintValidatorContext): Boolean {
        val userDto = when (value) {
            is UserDto -> value
            is TeacherSignUpDTO -> value.user.toUserDTO(UserType.GUEST)
            is TranslatorSignUpDTO -> value.user.toUserDTO(UserType.GUEST)
            is ParentSignUpDTO -> value.user.toUserDTO(UserType.GUEST)
            is AdminSignUpDTO -> value.toUserDTO()
            // 다른 DTO 유형에 대한 처리 추가
            else -> throw IllegalArgumentException("Unsupported DTO type")
        }
        validUser(userDto)
        return true
    }

    private fun validUser(userDto: UserDto) {
        // for suspend fun
        runBlocking {
            if (userService.existUserByUserName(userDto.userName))
                throw DuplicatedException("이미 존재하는 아이디입니다.")

            if (userService.existUserByEmail(userDto.email))
                throw DuplicatedException("이미 존재하는 이메일입니다.")

        }
    }
}