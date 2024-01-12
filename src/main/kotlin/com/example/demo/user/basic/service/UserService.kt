package com.example.demo.user.basic.service

import com.example.demo.profile.dto.UserEditDTO
import com.example.demo.signup.validation.SignUpValid
import com.example.demo.user.basic.dto.UserDto
import com.example.demo.user.basic.entity.User
import com.example.demo.user.basic.exception.UserNotFoundException
import com.example.demo.user.basic.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(private val userRepository: UserRepository) {


    @Transactional
    fun signUp(userDto: UserDto): Long {
        return userRepository.save(userDto.toEntity()).id!!
    }

    @Transactional(readOnly = true)
    fun existUser(id: Long) = userRepository.existsById(id)

    @Transactional(readOnly = true)
    fun existUser(username: String) = userRepository.findByUsername(username).isPresent

    @Transactional(readOnly = true)
    fun getUserEntity(id: Long): User {
        return userRepository
            .findById(id)
            .orElseThrow {
                UserNotFoundException(id, "해당하는 유저가 없습니다.")
            }
    }

    @Transactional(readOnly = true)
    fun getUser(id: Long): UserDto {
        return UserDto(getUserEntity(id))
}

@Transactional
suspend fun updateProfile(id: Long, userEditDTO: UserEditDTO) {
    val existingUser = userRepository.findById(id).orElseThrow {
        UserNotFoundException(id, "일치하는 사용자가 없습니다")
    }
    existingUser.name = userEditDTO.name
    existingUser.phone = userEditDTO.phone
    existingUser.email = userEditDTO.email
    existingUser.language = userEditDTO.languages

    userRepository.save(existingUser)
}
}

//suspend fun updateUser(userId: Long, userDto: UserDto) {
//    val existingUser = userRepository.findById(userId).orElseThrow { NotFoundException("User not found with id: $userId") }
//
//    // Update user-specific fields
//    existingUser.userName = userDto.userName
//    existingUser.password = userDto.password // Note: You might want to handle password updates more securely
//
//    // Update any other fields as needed
//
//    // Save the updated user
//    userRepository.save(existingUser)
//}