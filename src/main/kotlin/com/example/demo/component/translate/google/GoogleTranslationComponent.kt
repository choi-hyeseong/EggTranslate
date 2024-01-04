package com.example.demo.component.translate.google

import com.google.cloud.translate.Translate
import com.google.cloud.translate.TranslateOptions
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component

@Component
class GoogleTranslationComponent {

    @Value("\${google.translate.api-key}")
    private lateinit var apiKey: String

    @Bean
    fun provideGoogleTranslator(): Translate {
        return TranslateOptions.newBuilder().setApiKey(apiKey).build().service
    }
}