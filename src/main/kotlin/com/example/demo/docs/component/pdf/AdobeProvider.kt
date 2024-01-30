package com.example.demo.docs.component.pdf

import com.adobe.pdfservices.operation.PDFServices
import com.adobe.pdfservices.operation.auth.ServicePrincipalCredentials
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component

@Component
class AdobeProvider(
    @Value("\${adobe-pdf-client-id}")
    private val clientId : String,
    @Value("\${adobe-pdf-client-secret}")
    private val clientSecret : String
) {


    @Bean
    fun provideAdobeCredential() : ServicePrincipalCredentials = ServicePrincipalCredentials(clientId, clientSecret)

    @Bean
    fun providePDFService(credentials: ServicePrincipalCredentials) : PDFServices = PDFServices(credentials)
}