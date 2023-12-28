package com.example.demo.config

import com.google.cloud.vision.v1.ImageAnnotatorClient
import com.google.cloud.vision.v1.ImageAnnotatorSettings
import org.apache.http.client.CredentialsProvider
import org.springframework.cloud.gcp.core.Credentials
import org.springframework.cloud.gcp.core.DefaultCredentialsProvider
import org.springframework.cloud.gcp.vision.CloudVisionTemplate
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class VisionConfig {

    @Bean
    fun cloudVisionTemplate(imageAnnotatorClient: ImageAnnotatorClient): CloudVisionTemplate {
        return CloudVisionTemplate(imageAnnotatorClient)
    }

    @Bean
    fun imageAnnotator(): ImageAnnotatorClient {
        val settings: ImageAnnotatorSettings = ImageAnnotatorSettings.newBuilder()
                .setCredentialsProvider(DefaultCredentialsProvider { Credentials() })
                .build()
        return ImageAnnotatorClient.create(settings)
    }
}