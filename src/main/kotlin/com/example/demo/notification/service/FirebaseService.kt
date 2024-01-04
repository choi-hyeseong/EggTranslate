package com.example.demo.notification.service

import com.example.demo.common.response.Response
import com.example.demo.notification.component.FirebaseMessenger
import com.example.demo.notification.dto.push.FirebaseRequestDTO
import com.example.demo.notification.dto.push.FirebaseResponseDTO

@Deprecated("Unused")
class FirebaseService(private val firebaseMessenger: FirebaseMessenger) {

    suspend fun notify(requestDTO: FirebaseRequestDTO) : Response<FirebaseResponseDTO> {
        return notify(listOf(requestDTO))
    }

    suspend fun notify(requests : List<FirebaseRequestDTO>) : Response<FirebaseResponseDTO> {
        return firebaseMessenger.requestNotification(requests)
    }
}