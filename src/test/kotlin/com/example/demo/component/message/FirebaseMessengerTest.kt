package com.example.demo.component.message

import com.example.demo.notification.component.FirebaseMessenger
import com.example.demo.notification.dto.push.FirebaseRequestDTO
import com.google.firebase.messaging.BatchResponse
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.SendResponse
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class FirebaseMessengerTest {

    @Test
    @DisplayName("리스트가 빈값이면 호출되지 않음")
    fun TEST_NOT_CALLED() {
        val messaging = mockk<FirebaseMessaging>()
        val messenger = FirebaseMessenger(messaging)
        runBlocking {
            val response = messenger.requestNotification(listOf())
            assertFalse(response.isSuccess)
            assertTrue(response.data?.isEmpty()!!)
            verify(exactly = 0) { messaging.sendEach(any()) }
        }
    }

    @Test
    @DisplayName("한사람 호출")
    fun TEST_FOR_ONE() {
        val messaging = mockk<FirebaseMessaging>()
        val messenger = FirebaseMessenger(messaging)
        val requestContent = FirebaseRequestDTO("adsf", "", "")
        every { messaging.sendEach(any()) } returns object : BatchResponse {
            override fun getResponses(): MutableList<SendResponse> {
                return mutableListOf()
            }

            override fun getSuccessCount(): Int {
                return 1
            }

            override fun getFailureCount(): Int {
                return 0
            }
        }
        runBlocking {
            val response = messenger.requestNotification(listOf(requestContent))
            assertEquals(response.data?.successCount, 1)
            assertTrue(response.isSuccess)
            assertEquals(response.data?.failCount, 0)
            //assertEquals(listSlot.captured[0].getToken())
        }
    }

    @Test
    @DisplayName("450명 호출")
    fun TEST_FOR_LIMIT() {
        val messaging = mockk<FirebaseMessaging>()
        val messenger = FirebaseMessenger(messaging)
        val requestContent = FirebaseRequestDTO("asdf", "", "")
        val list = mutableListOf<FirebaseRequestDTO>()
        for (i in 0..499) {
            list.add(requestContent)
        }
        every { messaging.sendEach(any()) } returns object : BatchResponse {
            override fun getResponses(): MutableList<SendResponse> {
                return mutableListOf()
            }

            override fun getSuccessCount(): Int {
                return list.size
            }

            override fun getFailureCount(): Int {
                return 0
            }
        }
        runBlocking {
            val response = messenger.requestNotification(listOf(requestContent))
            assertEquals(response.data?.successCount, list.size)
            assertTrue(response.isSuccess)
            assertEquals(response.data?.failCount, 0)
            //assertEquals(listSlot.captured[0].getToken())
        }
    }

    @Test
    @DisplayName("550명 호출")
    fun TEST_FOR_UPPER_LIMIT() {
        val messaging = mockk<FirebaseMessaging>()
        val messenger = FirebaseMessenger(messaging)
        val requestContent = FirebaseRequestDTO("asdf", "", "")
        val list = mutableListOf<FirebaseRequestDTO>()
        for (i in 0..550) {
            list.add(requestContent)
        }
        every { messaging.sendEach(any()) } returns object : BatchResponse {
            override fun getResponses(): MutableList<SendResponse> {
                return mutableListOf()
            }

            override fun getSuccessCount(): Int {
                return list.size
            }

            override fun getFailureCount(): Int {
                return 0
            }
        }
        runBlocking {
            val response = messenger.requestNotification(listOf(requestContent))
            assertEquals(response.data?.successCount, list.size)
            assertTrue(response.isSuccess)
            assertEquals(response.data?.failCount, 0)
            //assertEquals(listSlot.captured[0].getToken())
        }
    }
}