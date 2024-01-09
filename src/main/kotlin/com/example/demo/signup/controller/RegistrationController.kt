package com.example.demo.signup

import com.example.demo.signup.service.RegistrationService
import com.example.demo.user.basic.repository.UserRepository
import com.example.demo.user.parent.dto.ParentDTO
import com.example.demo.user.teacher.dto.TeacherDTO
import com.example.demo.user.translator.dto.TranslatorDTO
import lombok.RequiredArgsConstructor
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequiredArgsConstructor
@RequestMapping("api/register")
class RegistrationController(private val registrationService: RegistrationService) {


    @PostMapping("/language") // 언어 선택
    suspend fun selectLang(@RequestParam lang : String) : String {
        return lang;
    }

    @PostMapping("/userType") // 유저 타입 선택 (학부모, 선생님, 번역가)
    suspend fun selectType(@RequestParam userType : String) : String {
        return userType;
    }

    @PostMapping("/teacher")
    fun registerParent(@RequestBody teacherDTO: TeacherDTO): ResponseEntity<String> {
        try {
            RegistrationService.registerTeacher(teacherDTO)
            return ResponseEntity.ok("Teacher registered successfully.")
        } catch (e: Exception) {
            return ResponseEntity.status(500).body("Failed to register teacher.")
        }
    }

    @PostMapping("/parent")
    fun registerParent(@RequestBody parentDTO: ParentDTO): ResponseEntity<String> {
        try {
            RegistrationService.registerParent(parentDTO)
            return ResponseEntity.ok("Parent registered successfully.")
        } catch (e: Exception) {
            return ResponseEntity.status(500).body("Failed to register teacher.")
        }
    }

    @PostMapping("/translator")
    fun registerParent(@RequestBody translatorDTO: TranslatorDTO): ResponseEntity<String> {
        try {
            RegistrationService.registerTranslator(translatorDTO)
            return ResponseEntity.ok("Translator registered successfully.")
        } catch (e: Exception) {
            return ResponseEntity.status(500).body("Failed to register teacher.")
        }
    }
}
