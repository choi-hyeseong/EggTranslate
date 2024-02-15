package com.example.demo.member.user.service

import com.example.demo.member.user.data.DataFetcher
import com.example.demo.common.page.Pageable
import com.example.demo.convertOrNull
import com.example.demo.member.user.entity.User
import com.example.demo.member.exception.UserException
import com.example.demo.member.user.repository.UserRepository
import com.example.demo.member.user.type.UserType
import com.example.demo.member.user.dto.*
import org.springframework.data.domain.PageRequest
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import kotlin.jvm.optionals.getOrNull
import kotlin.math.max

@Service
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) : DataFetcher<UserListItemDTO, UserResponseDTO> {

    //로그인시 없는 유저이거나, 혹은 패스워드가 일치 하지 않을경우 로그인 실패 exception
    @Transactional
    fun login(userLoginDTO: UserLoginDTO): UserDto {
        val user = userRepository.findByUsername(userLoginDTO.userName).getOrNull() //아이디 존재 여부를 알려줘선 안됨
        if (user == null || !passwordEncoder.matches(userLoginDTO.password, user.password)) //match 하지 않는경우..
            throw UserException("로그인에 실패 하였습니다.")

        return UserDto(user)
    }

    @Transactional
    suspend fun signUp(userDto: UserDto): Long? {
        return userRepository.save(userDto.apply { password = passwordEncoder.encode(password) }.toEntity()).id
    }

    @Transactional(readOnly = true)
    suspend fun existUser(id: Long) = userRepository.existsById(id)

    @Transactional(readOnly = true)
    suspend fun existByUserType(userType: UserType) = userRepository.existsByUserType(userType)

    @Transactional(readOnly = true)
    suspend fun existUserByUserName(userName: String) = userRepository.existsByUsername(userName)

    @Transactional(readOnly = true)
    suspend fun existUserByEmail(email : String) = userRepository.existsByEmail(email)

    @Transactional(readOnly = true)
    fun findUserByEmail(email : String) : UserDto = UserDto(userRepository.findByEmail(email).orElseThrow { UserException("잘못된 유저 정보입니다.") })

    @Transactional(readOnly = true)
    suspend fun findUserByUserName(userName : String) : UserDto = UserDto(userRepository.findByUsername(userName).orElseThrow { UserException("잘못된 유저 정보입니다.") })

    @Transactional(readOnly = true)
    suspend fun getUserEntity(id: Long): User {
        return userRepository
            .findById(id)
            .orElseThrow {
                UserException("해당하는 유저가 없습니다. Id : $id")
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
    suspend fun updateUserType(id: Long, userType: UserType) {
        val user = getUserEntity(id)
        user.userType = userType
        userRepository.save(user)
    }

    @Transactional
    suspend fun deleteById(id: Long) {
        // TODO 갖고 있는 모든 연관관계 제거
        userRepository.deleteById(id)
    }


    @Transactional
    suspend fun updateUser(id: Long, userUpdateDTO: UserUpdateDTO?) {
        val user = getUserEntity(id)
        if (userUpdateDTO != null) {
            user.update(userUpdateDTO)
            userRepository.save(user)
        }
    }

    @Transactional
    override suspend fun getList(page: Int, amount: Int): Pageable<UserListItemDTO> {
        val pageUser = userRepository.findAll(PageRequest.of(page, amount))
        return Pageable(page, max(0, pageUser.totalPages - 1), pageUser.content.map { UserListItemDTO(it) })
    }

    @Transactional
    override suspend fun getDetail(id: Long): UserResponseDTO {
        return getUser(id).toResponseDTO()
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