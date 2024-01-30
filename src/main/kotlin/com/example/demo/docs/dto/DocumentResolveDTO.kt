package com.example.demo.docs.dto

import com.example.demo.voca.dto.VocaResponseDTO

class DocumentResolveDTO (
    val documentReadResponse: DocumentReadResponse,
    val documentWriteResponse: DocumentWriteResponse,
    val documentDTO: DocumentDTO,
    val convertDocumentDTO: ConvertDocumentDTO,
    val voca : MutableList<VocaResponseDTO>
)