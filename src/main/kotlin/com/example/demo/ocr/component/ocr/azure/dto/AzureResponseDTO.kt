package com.example.demo.ocr.component.ocr.azure.dto

import com.example.demo.ocr.component.ocr.model.Paragraph

class AzureResponseDTO(
    val content : String,
    val paragraphs : List<Paragraph>
)