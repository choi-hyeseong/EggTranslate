package com.example.demo

import com.example.demo.common.response.Response
import com.example.demo.translate.dto.TranslateRequestDTO
import com.example.demo.translate.dto.TranslateResponseDTO
import com.example.demo.translate.service.TranslateService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/test")
class TestController(private val translateService: TranslateService) {

    //for test
    @PostMapping("/")
    suspend fun image(@RequestBody translateRequestDTO: TranslateRequestDTO) : ResponseEntity<Response<TranslateResponseDTO>> {
        val result = translateService.translate(translateRequestDTO)
        return if (result.isSuccess)
            ResponseEntity(result, HttpStatus.OK)
        else
            ResponseEntity(result, HttpStatus.BAD_REQUEST)
    }

//    @PostMapping("/firebase")
//    suspend fun notify(@RequestBody firebaseRequestDTO: List<FirebaseRequestDTO>) : Response<FirebaseResponseDTO> {
//        return firebaseMessenger.requestNotification(firebaseRequestDTO)
//    }



}