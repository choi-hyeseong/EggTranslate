package com.example.demo.signup.validation

import com.example.demo.common.exception.ParameterNotValidException
import com.example.demo.signup.exception.DuplicatedUsernameException
import com.example.demo.user.basic.dto.UserDto
import com.example.demo.user.basic.repository.UserRepository
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile

@Component
@RequiredArgsConstructor
class SignUpValidator(private val userRepository: UserRepository) : ConstraintValidator<SignUpValid, UserDto> {

    /* 비밀번호도 형식이 필요하다면 사용하면 되겠음.*/
//    override fun isValid(userDto: UserDto, context: ConstraintValidatorContext) : Boolean {
//        return isUsernameValid(userDto.username) && isPasswordValid(userDto.password)
//    }
    override fun isValid(userDto: UserDto, context: ConstraintValidatorContext): Boolean {
        if(!isUsernameValid(userDto.userName)) // 존재하는 아이디일 경우
            throw DuplicatedUsernameException("이미 존재하는 아이디입니다")

        return true
    }

    private fun isUsernameValid(username: String): Boolean {
        return userRepository.findByUsername(username)==null // --> user에 존재하지 않는 아이디
    }
}