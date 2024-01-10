package com.example.demo.signup.validation

import com.example.demo.signup.exception.DuplicatedUsernameException
import com.example.demo.user.basic.repository.UserRepository
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Component

@Component
@RequiredArgsConstructor
class UsernameValidator(private val userRepository: UserRepository) : ConstraintValidator<UsernameValid, String> {
    override fun isValid(p0: String, p1: ConstraintValidatorContext?) : Boolean {
        if(userRepository.findByUsername(p0)!=null) {
            throw DuplicatedUsernameException("이미 존재하는 아이디입니다")
        }
        return true
    }
}
