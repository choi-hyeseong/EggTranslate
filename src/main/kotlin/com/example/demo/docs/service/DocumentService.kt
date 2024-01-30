package com.example.demo.docs.service

import com.example.demo.docs.dto.ConvertDocumentDTO
import com.example.demo.docs.dto.DocumentDTO
import com.example.demo.docs.entity.ConvertDocument
import com.example.demo.docs.entity.Document
import com.example.demo.docs.exception.DocumentException
import com.example.demo.docs.repository.ConvertDocumentRepository
import com.example.demo.docs.repository.DocumentRepository
import com.example.demo.user.basic.service.UserService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import kotlin.jvm.optionals.getOrNull

@Service
class DocumentService(
    private val userService: UserService,
    private val documentRepository: DocumentRepository,
    private val convertDocumentRepository: ConvertDocumentRepository
) {

    @Transactional
    suspend fun saveDocument(documentDTO: DocumentDTO): Document {
        val user = userService.getUserEntityOrNull(documentDTO.userDto?.id)
        return documentRepository.save(documentDTO.toEntity(user))
    }

    @Transactional
    suspend fun findDocumentById(id: Long): DocumentDTO {
        return DocumentDTO(findDocumentEntityById(id))
    }

    @Transactional
    suspend fun findDocumentEntityById(id: Long): Document {
        return documentRepository.findById(id).orElseThrow { DocumentException("존재하지 않는 문서입니다.") }
    }

    @Transactional
    suspend fun findDocumentEntityByIdOrNull(id: Long?): Document? {
        if (id == null)
            return null
        return documentRepository.findById(id).getOrNull()
    }

    @Transactional
    suspend fun findConvertDocumentById(id: Long): ConvertDocumentDTO {
        return ConvertDocumentDTO(findConvertDocumentEntityById(id))
    }

    @Transactional
    suspend fun findConvertDocumentEntityById(id: Long): ConvertDocument {
        return convertDocumentRepository.findById(id).orElseThrow { DocumentException("존재하지 않는 변환된 문서입니다.") }
    }

    @Transactional
    suspend fun deleteAllDocumentByUserId(userId : Long) {
        documentRepository.deleteAllByUserId(userId)
    }
}