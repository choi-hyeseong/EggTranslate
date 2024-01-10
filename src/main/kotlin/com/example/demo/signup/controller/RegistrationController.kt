package com.example.demo.signup.controller

import com.azure.core.annotation.Post
import com.example.demo.common.response.Response
import com.example.demo.signup.dto.ParentSignUpDTO
import com.example.demo.signup.dto.TeacherSignUpDTO
import com.example.demo.signup.dto.TranslatorSignUpDTO
import com.example.demo.signup.service.RegistrationService
import com.example.demo.signup.validation.SignUpValid
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

    @PostMapping("/userType") // 유저 타입 선택 (학부모, 선생님, 번역가)
    suspend fun selectType(@RequestParam userType : String) : String {
        return userType
    }

    @PostMapping("/teacher")
    fun registerTeacher(@RequestBody @SignUpValid teacherSignUpDTO: TeacherSignUpDTO): Response<TeacherDTO> = Response.ofSuccess(null, registrationService.registerTeacher(teacherSignUpDTO))

    @PostMapping("/parent")
    fun registerParent(@RequestBody @SignUpValid parentSignUpDTO: ParentSignUpDTO) : Response<ParentDTO> = Response.ofSuccess(null, registrationService.registerParent(parentSignUpDTO))

    @PostMapping("/translator")
    fun registerTranslator(@RequestBody @SignUpValid translatorSignUpDTO: TranslatorSignUpDTO) : Response<TranslatorDTO> = Response.ofSuccess(null, registrationService.registerTranslator(translatorSignUpDTO))

//    @PostMapping("/parent")
//    fun registerParent(@RequestBody parentDTO: ParentSignUpDTO): ResponseEntity<String> {
//        try {
//            registrationService.registerParent(parentDTO)
//            return ResponseEntity.ok("Parent registered successfully.")
//        } catch (e: Exception) {
//            return ResponseEntity.status(500).body("Failed to register parent.")
//        }
}