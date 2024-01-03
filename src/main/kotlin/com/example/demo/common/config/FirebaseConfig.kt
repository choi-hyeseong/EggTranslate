package com.example.demo.common.config

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.messaging.FirebaseMessaging
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.io.File

@Deprecated("Unused")
class FirebaseConfig {

    @Value("\${firebase.credential_location}")
    private lateinit var credentialPath: String

    @Bean
    fun provideFireBaseMessaging(): FirebaseMessaging {
        val firebaseApp: FirebaseApp
        File(credentialPath).inputStream().use {
            val appList = FirebaseApp.getApps()
            firebaseApp = if (appList.isNullOrEmpty())
                FirebaseApp.initializeApp(
                    FirebaseOptions.builder().setCredentials(GoogleCredentials.fromStream(it)).build()
                )
            else
                FirebaseApp.getApps().find { app -> app.name.equals(FirebaseApp.DEFAULT_APP_NAME) }!!
        }
        return FirebaseMessaging.getInstance(firebaseApp)
    }
}