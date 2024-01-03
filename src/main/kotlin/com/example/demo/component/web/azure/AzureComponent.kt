package com.example.demo.component.web.azure

import com.azure.ai.formrecognizer.documentanalysis.DocumentAnalysisClient
import com.azure.ai.formrecognizer.documentanalysis.DocumentAnalysisClientBuilder
import com.azure.core.credential.AzureKeyCredential
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component

@Component
class AzureComponent {

    @Value("\${azure.ocr.key}")
    private lateinit var azureKey: String

    @Value("\${azure.ocr.end-point}")
    private lateinit var azureEndpoint: String

    @Bean
    fun provideAzureClient(): DocumentAnalysisClient {
        return DocumentAnalysisClientBuilder()
            .credential(AzureKeyCredential(azureKey))
            .endpoint(azureEndpoint)
            .buildClient()
    }
}