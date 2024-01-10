package com.example.demo.signup.validation

import com.example.demo.signup.dto.ParentSignUpDTO
import com.example.demo.signup.dto.TeacherSignUpDTO
import com.example.demo.signup.dto.TranslatorSignUpDTO
import com.example.demo.signup.exception.DuplicatedUsernameException
import com.example.demo.user.basic.dto.UserDto
import com.example.demo.user.basic.repository.UserRepository
import com.example.demo.user.basic.service.UserService
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
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
        when (value) {
            is UserDto -> return isUsernameValid(value.userName)
            is TeacherSignUpDTO -> {
                if(!isUsernameValid(value.user.userName))
                    throw DuplicatedUsernameException("이미 존재하는 아이디입니다.")
                return true
            }
            is TranslatorSignUpDTO -> {
                if(!isUsernameValid(value.user.userName))
                    throw DuplicatedUsernameException("이미 존재하는 아이디입니다.")
                return true
            }
            is ParentSignUpDTO -> {
                if(!isUsernameValid(value.user.userName))
                    throw DuplicatedUsernameException("이미 존재하는 아이디입니다.")
                return true
            }
            // 다른 DTO 유형에 대한 처리 추가
            else -> throw IllegalArgumentException("Unsupported DTO type")
        }
    }

    private fun isUsernameValid(username: String): Boolean {
        return !userService.existUser(username) // --> user에 존재하지 않는 아이디
    }
}