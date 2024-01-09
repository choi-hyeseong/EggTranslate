package com.example.demo.user.basic.service

import com.example.demo.user.basic.dto.UserDto
import com.example.demo.user.basic.exception.UserNotFoundException
import com.example.demo.user.basic.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(private val userRepository: UserRepository) {


    @Transactional
    fun signUp(userDto: UserDto): Boolean {
        return userRepository.save(userDto.toEntity()).id != -1L
    }

    @Transactional(readOnly = true)
    fun getUser(id: Long): UserDto {
        return UserDto(userRepository
            .findById(id)
            .orElseThrow { UserNotFoundException(id, "해당하는 유저가 없습니다.") }
        )
    }
}