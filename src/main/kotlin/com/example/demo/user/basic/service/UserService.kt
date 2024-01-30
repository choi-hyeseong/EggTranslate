package com.example.demo.user.basic.service

import com.example.demo.convertOrNull
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
    suspend fun signUp(userDto: UserDto): Long? {
        return userRepository.save(userDto.toEntity()).id
    }

    @Transactional(readOnly = true)
    suspend fun existUser(id: Long) = userRepository.existsById(id)

    @Transactional(readOnly = true)
    suspend fun existUser(username: String) = userRepository.findByUsername(username).isPresent

    @Transactional(readOnly = true)
    suspend fun getUserEntity(id: Long): User {
        return userRepository
            .findById(id)
            .orElseThrow {
                UserNotFoundException(id, "해당하는 유저가 없습니다.")
            }
    }


    @Transactional(readOnly = true)
    suspend fun getUserEntityOrNull(id: Long?): User? {
        return id.convertOrNull { getUserEntity(it) }
    }

    @Transactional(readOnly = true)
    suspend fun getUser(id: Long): UserDto {
        return UserDto(getUserEntity(id))
    }

    @Transactional(readOnly = true)
    suspend fun getUserOrNull(id: Long?): UserDto? {
        if (id == null)
            return null
        return UserDto(getUserEntity(id))
    }


    @Transactional
    suspend fun updateProfile(id: Long, userEditDTO: UserEditDTO) {
        val existingUser = getUserEntity(id)
        existingUser.name = userEditDTO.name
        existingUser.phone = userEditDTO.phone
        existingUser.email = userEditDTO.email
        existingUser.language = userEditDTO.languages

        userRepository.save(existingUser)
    }

    @Transactional
    suspend fun deleteById(id : Long) {
        // TODO 갖고 있는 모든 연관관계 제거
        userRepository.deleteById(id)
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