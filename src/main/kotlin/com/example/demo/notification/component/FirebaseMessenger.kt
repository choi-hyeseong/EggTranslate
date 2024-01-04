package com.example.demo.notification.component

import com.example.demo.common.response.Response
import com.example.demo.notification.dto.push.FirebaseRequestDTO
import com.example.demo.notification.dto.push.FirebaseResponseDTO
import com.example.demo.logger
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingException
import com.google.firebase.messaging.Message
import com.google.firebase.messaging.Notification

@Deprecated("Unused")
class FirebaseMessenger(private val firebaseMessaging: FirebaseMessaging) {

    private val log = logger()

    // list로 받는 이유 = api 1회 call 마다 500개 limit -> 끊어서 보내기
    suspend fun requestNotification(requests: List<FirebaseRequestDTO>): Response<FirebaseResponseDTO> {
        var response : FirebaseResponseDTO = FirebaseResponseDTO.empty()
        var count = 0
        var startIndex = 0
        var endIndex = 0
        while (true) {
            //과부하 방지
            if (count >= 100000)
                break
            //리스트의 크기 - 시작값 = 현재 남은 요청수
            //만약 500개 이상의 요청이 남아있을경우 500개까지 한정. 아닌경우 끝까지
            endIndex = if (requests.size - startIndex > 500) startIndex + 500 else requests.size
            if (startIndex == endIndex)
                break
            //startIndex ~ endIndex - 1까지 추출
            response = response.merge(callNotification(requests.subList(startIndex, endIndex)))
            startIndex = endIndex
            count++
        }

        return if (response.failCount > 0 || response.isEmpty())
            Response.ofFailure(null, response)
        else
            Response.ofSuccess(null, response)
    }

    private suspend fun callNotification(requests: List<FirebaseRequestDTO>): FirebaseResponseDTO {
        var successCount : Int = 0
        var failCount : Int = 0
        var message : String
        try {
            val response = firebaseMessaging.sendEach(requests.map { request ->
                Message.builder().setToken(request.id)
                    .setNotification(
                        Notification.builder().setTitle(request.title)
                            .setBody(request.content)
                            .build()
                    )
                    .build()
            }.toList())
            successCount = response.successCount
            failCount = response.failureCount
            message = "정상적으로 처리되었습니다."
        }
        catch (e: FirebaseMessagingException) {
            log.error("FirebaseMessaging has an error : {}, code : {}", e.message, e.messagingErrorCode)
            failCount++
            message = e.message ?: "메시지 요청 도중 오류가 발생했습니다. 관리자에게 문의하십시오"
        }
        return FirebaseResponseDTO(successCount, failCount, message)
    }


}