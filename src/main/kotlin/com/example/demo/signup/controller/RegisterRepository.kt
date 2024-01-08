package com.example.demo.signup

import com.example.demo.user.basic.dto.UserDto
import com.example.demo.user.basic.entity.User
import com.example.demo.user.basic.repository.UserRepository
import lombok.RequiredArgsConstructor
import org.springframework.web.bind.annotation.*

@RestController
@RequiredArgsConstructor
@RequestMapping("api/register")
class RegisterController(private val userRepository: UserRepository) {


    @PostMapping("/language") // 언어 선택
    suspend fun selectLang(@RequestParam lang : String) : String {
        return lang;
    }

    @PostMapping("/userType") // 유저 타입 선택 (학부모, 선생님, 번역가)
    suspend fun selectType(@RequestParam userType : String) : String {
        return userType;
    }

    @PostMapping("/signup/teacher")
    suspend fun createTeacher(@RequestBody userDto: UserDto) : User {
        val user = userDto.toEntity(userDTO.id, userDTO.name, userDTO.password, userDTO.phone, userDTO.email, userDTO.languages)
        return userRepository.save(user);
    }
}
