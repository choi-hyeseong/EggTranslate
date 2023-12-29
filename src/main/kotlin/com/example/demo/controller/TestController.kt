package com.example.demo.controller

import com.example.demo.common.response.ObjectResponse
import com.example.demo.component.message.FirebaseMessenger
import com.example.demo.dto.push.FirebaseRequestDTO
import com.example.demo.dto.push.FirebaseResponseDTO
import com.example.demo.dto.translate.TranslateRequestDTO
import com.example.demo.dto.translate.TranslateResponseDTO
import com.example.demo.service.TranslateService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/test")
class TestController(private val translateService: TranslateService, private val firebaseMessenger: FirebaseMessenger) {

    //for test
    @PostMapping("/")
    suspend fun image(@RequestBody translateRequestDTO: TranslateRequestDTO) : ResponseEntity<ObjectResponse<TranslateResponseDTO>> {
        val result = translateService.translate(translateRequestDTO)
        return if (result.isSuccess)
            ResponseEntity(result, HttpStatus.OK)
        else
            ResponseEntity(result, HttpStatus.BAD_REQUEST)
    }

    @PostMapping("/firebase")
    suspend fun notify(@RequestBody firebaseRequestDTO: List<FirebaseRequestDTO>) : ObjectResponse<FirebaseResponseDTO> {
        return firebaseMessenger.requestNotification(firebaseRequestDTO)
    }



}