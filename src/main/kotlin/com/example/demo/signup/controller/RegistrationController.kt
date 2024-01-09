package com.example.demo.signup.controller

import com.azure.core.annotation.Post
import com.example.demo.common.response.Response
import com.example.demo.signup.dto.ParentSignUpDTO
import com.example.demo.signup.dto.TeacherSignUpDTO
import com.example.demo.signup.dto.TranslatorSignUpDTO
import com.example.demo.signup.service.RegistrationService
import com.example.demo.user.parent.dto.ParentDTO
import com.example.demo.user.parent.entity.Parent
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
        return lang
    }
//    @PostMapping("/language") // 언어 선택
//    suspend fun selectLang(@RequestBody lang : String) : String {
//        return lang
//    }

    @PostMapping("/userType") // 유저 타입 선택 (학부모, 선생님, 번역가)
    suspend fun selectType(@RequestParam userType : String) : String {
        return userType
    }
//    @PostMapping("/userType") // 유저 타입 선택 (학부모, 선생님, 번역가)
//    suspend fun selectType(@RequestBody userType : String) : String {
//        return userType
//    }

    @PostMapping("/teacher")
    fun registerTeacher(@RequestBody teacherSignUpDTO: TeacherSignUpDTO): Response<TeacherDTO> = Response.ofSuccess(null, registrationService.registerTeacher(teacherSignUpDTO))

    @PostMapping("/parent")
    fun registerParent(@RequestBody parentSignUpDTO: ParentSignUpDTO) : Response<ParentDTO> = Response.ofSuccess(null, registrationService.registerParent(parentSignUpDTO))
//    @PostMapping("/parent")
//    fun registerParent(@RequestBody parentDTO: ParentSignUpDTO): ResponseEntity<String> {
//        try {
//            registrationService.registerParent(parentDTO)
//            return ResponseEntity.ok("Parent registered successfully.")
//        } catch (e: Exception) {
//            return ResponseEntity.status(500).body("Failed to register parent.")
//        }
//    }

    @PostMapping("/translator")
    fun registerTranslator(@RequestBody translatorSignUpDTO: TranslatorSignUpDTO) : Response<TranslatorDTO> = Response.ofSuccess(null, registrationService.registerTranslator(translatorSignUpDTO))
//
//    @PostMapping("/translator")
//    fun registerParent(@RequestBody translatorDTO: TranslatorDTO): ResponseEntity<String> {
//        try {
//            registrationService.registerTranslator(translatorDTO)
//            return ResponseEntity.ok("Translator registered successfully.")
//        } catch (e: Exception) {
//            return ResponseEntity.status(500).body("Failed to register translator.")
//        }
}
