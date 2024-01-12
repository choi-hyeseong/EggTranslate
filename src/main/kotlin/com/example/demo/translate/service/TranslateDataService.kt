package com.example.demo.translate.service

import com.example.demo.file.service.FileService
import com.example.demo.translate.auto.dto.AutoTranslateDTO
import com.example.demo.translate.auto.dto.TranslateFileDTO
import com.example.demo.translate.auto.dto.TranslateResultDTO
import com.example.demo.translate.auto.entity.TranslateFile
import com.example.demo.translate.auto.entity.TranslateResult
import com.example.demo.translate.auto.repository.AutoTranslateRepository
import com.example.demo.translate.auto.repository.TranslateFileRepository
import com.example.demo.translate.auto.repository.TranslateResultRepository
import com.example.demo.translate.auto.service.AutoTranslateService
import com.example.demo.translate.manual.exception.ManualException
import com.example.demo.translate.exception.TranslateException
import com.example.demo.translate.manual.dto.ManualResultDTO
import com.example.demo.translate.manual.dto.ManualTranslateDTO
import com.example.demo.translate.manual.entity.ManualTranslate
import com.example.demo.translate.manual.repository.ManualResultRepository
import com.example.demo.translate.manual.repository.ManualTranslateRepository
import com.example.demo.user.basic.service.UserService
import com.example.demo.user.translator.service.TranslatorService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TranslateDataService(
    private val userService: UserService,
    private val fileService: FileService,
    private val translatorService: TranslatorService,
    private val translateFileRepository: TranslateFileRepository,
    private val autoTranslateService: AutoTranslateService,
    private val translateResultRepository: TranslateResultRepository,
    private val manualResultRepository: ManualResultRepository,
    private val manualTranslateRepository: ManualTranslateRepository
) {

    @Transactional
    fun findTranslateFileEntity(id: Long): TranslateFile {
        return translateFileRepository.findById(id)
            .orElseThrow { TranslateException("번역 데이터가 존재하지 않습니다.") }
    }

    @Transactional(readOnly = true)
    fun findTranslateFile(translateFileId: Long): TranslateFileDTO {
        return TranslateFileDTO(
            findTranslateFileEntity(translateFileId)
        )
    }

    @Transactional(readOnly = true)
    suspend fun findAllTranslateFiles(translateFileId: List<Long>): MutableList<TranslateFileDTO> {
        return translateFileRepository.findAllById(translateFileId).map {
            TranslateFileDTO(it)
        }.toMutableList()
    }


    @Transactional
    suspend fun saveTranslateResult(userId: Long, translateResultDTO: TranslateResultDTO): Long {
        val user = userService.getUserEntity(userId)
        val translateFiles = translateResultDTO.autoTranslate.translateFile.map {
            val file = fileService.findFileEntityById(it.file.id!!)
            it.toEntity(file)
        }.toMutableList()
        val autoTranslate = translateResultDTO.autoTranslate.toEntity(user, translateFiles)
        return translateResultRepository.save(translateResultDTO.toEntity(user, autoTranslate, null)).id!!
    }

    @Transactional
    suspend fun findTranslateResult(id: Long): TranslateResultDTO {
        return TranslateResultDTO(findTranslateResultEntity(id))
    }

    @Transactional
    suspend fun findTranslateResultEntity(id: Long): TranslateResult {
        return translateResultRepository.findById(id).orElseThrow { TranslateException("존재 하지 않는 번역 결과입니다.") }

    }

    @Transactional
    suspend fun findAllTranslateResultByUserId(id: Long): List<TranslateResultDTO> {
        return translateResultRepository.findAllByUserId(id).map {
            TranslateResultDTO(it)
        }.toMutableList()
    }





    @Transactional
    suspend fun saveManualResult(resultId: Long, translatorId: Long, resultDTO: ManualResultDTO): Long {
        val translateResultDTO = findTranslateResult(resultId)

        val user = userService.getUserEntity(translateResultDTO.user.id!!)
        val translator = translatorService.findTranslatorEntityById(translatorId)

        val autoTranslate = autoTranslateService.findAutoTranslateEntityById(translateResultDTO.autoTranslate.id!!)
        val manualResult = resultDTO.toEntity(translator, mutableListOf()) //처음 저장시에는 내부 데이터는 비어있음.

        val translateResultEntity = translateResultDTO.toEntity(user, autoTranslate, manualResult)
        return translateResultRepository.save(translateResultEntity).id!!
    }

    @Transactional
    suspend fun update(resultId: Long, translateFileId: Long, content: String) {
        if (existManualTranslateByTranslateFileId(translateFileId))
            throw ManualException("이미 번역된 내용입니다. 결과 ID : $resultId,  자동번역 ID : $translateFileId")

        val translateResult = findTranslateResultEntity(resultId)
        if (translateResult.manualResult == null)
            throw ManualException("생성되지 않은 번역 요청입니다. 결과 ID : $resultId")

        val translateFiles = translateResult.autoTranslate.translateFiles
        if (translateFiles.none { it.id == translateFileId })
            throw ManualException("해당 번역 요청에 포함되지 않은 자동 번역 데이터입니다. 결과 ID : $resultId, 자동번역 ID : $translateFileId")

        translateResult.manualResult!!.manualTranslate.add(
            ManualTranslate(null, findTranslateFileEntity(translateFileId), content)
        )

        // TODO 없애도 될지 체크
        translateResultRepository.save(translateResult)


    }

    @Transactional(readOnly = true)
    suspend fun manualResultExists(translateResultId: Long): Boolean {
        return findTranslateResult(translateResultId).manualResultDTO != null
    }

    @Transactional(readOnly = true)
    suspend fun existManualTranslateByTranslateFileId(translateFileId: Long): Boolean =
        manualTranslateRepository.existsByTranslateFileId(translateFileId)


}