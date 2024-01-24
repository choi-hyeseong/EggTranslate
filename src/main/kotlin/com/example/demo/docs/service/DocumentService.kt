package com.example.demo.docs.service

import com.example.demo.translate.service.TranslateService
import org.springframework.stereotype.Service

@Service
class DocumentService(
    private val translateService: TranslateService
) {

}