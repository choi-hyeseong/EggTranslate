package com.example.demo.docs.service

import com.example.demo.docs.component.DocumentResolver
import com.example.demo.docs.dto.ConvertDocumentDTO
import com.example.demo.docs.dto.DocumentDTO
import com.example.demo.docs.dto.DocumentRequestDTO
import com.example.demo.docs.dto.DocumentResolveDTO
import com.example.demo.docs.entity.ConvertDocument
import com.example.demo.docs.entity.Document
import com.example.demo.docs.exception.DocumentException
import com.example.demo.docs.factory.DocumentFactory
import com.example.demo.docs.repository.ConvertDocumentRepository
import com.example.demo.docs.repository.DocumentRepository
import com.example.demo.docs.type.DocumentType
import com.example.demo.file.util.FileUtil
import com.example.demo.translate.auto.dto.TranslateFileResponseDTO
import com.example.demo.translate.auto.dto.TranslateResultResponseDTO
import com.example.demo.translate.service.TranslateService
import com.example.demo.translate.web.dto.TranslateRequestDTO
import com.example.demo.user.basic.dto.UserDto
import com.example.demo.user.basic.service.UserService
import com.example.demo.user.parent.service.ParentService
import com.example.demo.voca.service.VocaService
import jakarta.transaction.Transactional
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.ByteArrayInputStream
import kotlin.jvm.optionals.getOrNull

//순환참조를 해결하기 위한 서비스. 실질적인 번역 요청에만 작동.
@Service
class DocumentTranslateService(
    private val documentService: DocumentService,
    private val documentResolver: DocumentResolver,
    private val translateService: TranslateService,
    private val userService: UserService,
    private val parentService: ParentService,
    private val vocaService: VocaService,
    private val documentFactory: DocumentFactory,
    @Value("\${image-path}") private val path : String
) {

    suspend fun request(requestDTO: DocumentRequestDTO): TranslateResultResponseDTO {
        val user = userService.getUserOrNull(requestDTO.userId)
        //read parallel logic
        val response = requestDocuments(requestDTO.lang, user, requestDTO.file)
        // return translated string
        val childDTO = parentService.findByChildIdOrNull(requestDTO.userId, requestDTO.childId)
        return translateService.saveTranslate(user, childDTO, response)


    }

    private suspend fun resolveFile(lang: String, file: MultipartFile, userDto: UserDto?): DocumentResolveDTO {
        val type = documentResolver.resolve(file)
        val document = saveDocumentFile(userDto, type, file)
        documentService.saveDocument(document)

        val parser = documentFactory.createParser(type, ByteArrayInputStream(file.bytes))
        val response = parser.read()
        val voca = vocaService.findAllContainingVoca(lang, response.content)
        val translate = translateService.requestWebTranslate(
            TranslateRequestDTO(
                "ko",
                lang,
                response.content
            )
        )
        val write = parser.write(translate.result ?: "", path.plus("/convertDocument"))
        val convertDocumentDTO = ConvertDocumentDTO(null, type, write.savePath, userDto)
        return DocumentResolveDTO(response, write, document, convertDocumentDTO, voca)
    }

    private suspend fun requestAsync(
        lang: String,
        userDto: UserDto?,
        documentFile: MultipartFile
    ): Deferred<TranslateFileResponseDTO> {
        val response = coroutineScope {
            async {
                val resolveResponse = resolveFile(lang, documentFile, userDto)
                val documentId = documentService.saveDocument(resolveResponse.documentDTO)
                TranslateFileResponseDTO(
                    null,
                    true,
                    null,
                    null,
                    documentId,
                    resolveResponse.convertDocumentDTO,
                    resolveResponse.voca,
                    "ko",
                    lang,
                    resolveResponse.documentReadResponse.content,
                    resolveResponse.documentWriteResponse.translate
                )
                //결과 반환시에는 origin에는 replace 된 값 보내줘선 안됨. 오직 origin 데이터.
            }
        }
        return response
    }

    private suspend fun requestDocuments(
        lang: String,
        userDto: UserDto?,
        documents: List<MultipartFile>
    ): List<TranslateFileResponseDTO> {
        return documents.map {
            requestAsync(lang, userDto, it)
        }.awaitAll()
    }



    suspend fun saveDocumentFile(userDto: UserDto?, type : DocumentType, file : MultipartFile) : DocumentDTO {
        val ext = FileUtil.findExtension(file.originalFilename ?: file.name)
        val savePath = path.plus("/document/${System.currentTimeMillis()}.$ext")
        FileUtil.saveFileWithCreateDir(file.bytes, savePath)
        return DocumentDTO(null, type, file.originalFilename ?: file.name, savePath, userDto)
    }


}