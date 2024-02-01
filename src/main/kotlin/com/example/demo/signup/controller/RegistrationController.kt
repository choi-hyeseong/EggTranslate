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

    @PostMapping("/teacher")
    suspend fun registerTeacher(@RequestBody @SignUpValid teacherSignUpDTO: TeacherSignUpDTO): Response<TeacherDTO> = Response.ofSuccess(null, registrationService.registerTeacher(teacherSignUpDTO))

    @PostMapping("/parent")
    suspend fun registerParent(@RequestBody @SignUpValid parentSignUpDTO: ParentSignUpDTO) : Response<ParentDTO> = Response.ofSuccess(null, registrationService.registerParent(parentSignUpDTO))

    @PostMapping("/translator")
    suspend fun registerTranslator(@RequestBody @SignUpValid translatorSignUpDTO: TranslatorSignUpDTO) : Response<TranslatorDTO> = Response.ofSuccess(null, registrationService.registerTranslator(translatorSignUpDTO))

//    @PostMapping("/parent")
//    fun registerParent(@RequestBody parentDTO: ParentSignUpDTO): ResponseEntity<String> {
//        try {
//            registrationService.registerParent(parentDTO)
//            return ResponseEntity.ok("Parent registered successfully.")
//        } catch (e: Exception) {
//            return ResponseEntity.status(500).body("Failed to register parent.")
//        }
}