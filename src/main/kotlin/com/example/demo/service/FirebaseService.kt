package com.example.demo.service

import com.example.demo.common.response.Response
import com.example.demo.component.message.FirebaseMessenger
import com.example.demo.dto.push.FirebaseRequestDTO
import com.example.demo.dto.push.FirebaseResponseDTO
import org.springframework.stereotype.Service

@Deprecated("Unused")
class FirebaseService(private val firebaseMessenger: FirebaseMessenger) {

    suspend fun notify(requestDTO: FirebaseRequestDTO) : Response<FirebaseResponseDTO> {
        return notify(listOf(requestDTO))
    }

    suspend fun notify(requests : List<FirebaseRequestDTO>) : Response<FirebaseResponseDTO> {
        return firebaseMessenger.requestNotification(requests)
    }
}