package com.example.demo.login.service

import com.example.demo.user.basic.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
@Transactional
class AuthService(private val userRepository: UserRepository) {

    fun authenticate(username: String, password: String): Boolean {
        val user = userRepository.findByUsername(username).get()

        // 사용자가 존재하고 비밀번호가 일치하는 경우에만 로그인 성공
        return user != null && user.password == password
    }
}