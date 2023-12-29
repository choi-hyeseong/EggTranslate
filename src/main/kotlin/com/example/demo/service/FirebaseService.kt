package com.example.demo.service

import com.example.demo.common.response.ObjectResponse
import com.example.demo.component.message.FirebaseMessenger
import com.example.demo.dto.push.FirebaseRequestDTO
import com.example.demo.dto.push.FirebaseResponseDTO
import org.springframework.stereotype.Service

@Service
class FirebaseService(private val firebaseMessenger: FirebaseMessenger) {

    suspend fun notify(requestDTO: FirebaseRequestDTO) : ObjectResponse<FirebaseResponseDTO> {
        return notify(listOf(requestDTO))
    }

    suspend fun notify(requests : List<FirebaseRequestDTO>) : ObjectResponse<FirebaseResponseDTO> {
        return firebaseMessenger.requestNotification(requests)
    }
}