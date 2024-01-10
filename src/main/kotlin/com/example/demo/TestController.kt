package com.example.demo

import com.example.demo.translate.service.TranslateService
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/test")
class TestController(private val translateService: TranslateService) {

    //for test
//    @PostMapping("/")
//    suspend fun image(@RequestBody autoTranslateRequestDTO: AutoTranslateRequestDTO) : ResponseEntity<Response<AutoTranslateResponseDTO>> {
//        val result = translateService.translate(autoTranslateRequestDTO)
//        return if (result.isSuccess)
//            ResponseEntity(result, HttpStatus.OK)
//        else
//            ResponseEntity(result, HttpStatus.BAD_REQUEST)
//    }

//    @PostMapping("/firebase")
//    suspend fun notify(@RequestBody firebaseRequestDTO: List<FirebaseRequestDTO>) : Response<FirebaseResponseDTO> {
//        return firebaseMessenger.requestNotification(firebaseRequestDTO)
//    }



}