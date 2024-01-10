package com.example.demo.user.basic.service

import com.example.demo.signup.validation.SignUpValid
import com.example.demo.user.basic.dto.UserDto
import com.example.demo.user.basic.exception.UserNotFoundException
import com.example.demo.user.basic.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(private val userRepository: UserRepository) {


    @Transactional
    fun signUp(userDto: UserDto): Long {
        return userRepository.save(userDto.toEntity()).id
    }

    @Transactional(readOnly = true)
    fun existUser(id : Long) = userRepository.existsById(id)

    @Transactional(readOnly = true)
    fun existUser(username : String) = userRepository.findByUsername(username).isPresent

    @Transactional(readOnly = true)
    fun getUser(id: Long): UserDto {
        return UserDto(userRepository
            .findById(id)
            .orElseThrow { UserNotFoundException(id, "해당하는 유저가 없습니다.") }
        )
    }
}