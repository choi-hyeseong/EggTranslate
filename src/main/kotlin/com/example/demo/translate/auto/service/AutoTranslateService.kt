package com.example.demo.translate.auto.service

import com.example.demo.file.service.FileService
import com.example.demo.translate.auto.dto.AutoTranslateDTO
import com.example.demo.translate.auto.dto.TranslateFileDTO
import com.example.demo.translate.auto.dto.TranslateResultDTO
import com.example.demo.translate.auto.entity.AutoTranslate
import com.example.demo.translate.auto.entity.TranslateFile
import com.example.demo.translate.auto.entity.TranslateResult
import com.example.demo.translate.auto.repository.AutoTranslateRepository
import com.example.demo.translate.auto.repository.TranslateResultRepository
import com.example.demo.translate.exception.TranslateException
import com.example.demo.translate.manual.dto.ManualResultDTO
import com.example.demo.translate.manual.dto.ManualTranslateRequestDTO
import com.example.demo.translate.manual.entity.ManualTranslate
import com.example.demo.translate.manual.exception.ManualException
import com.example.demo.translate.manual.repository.ManualResultRepository
import com.example.demo.translate.manual.repository.ManualTranslateRepository
import com.example.demo.translate.manual.type.TranslateState
import com.example.demo.user.basic.service.UserService
import com.example.demo.user.translator.service.TranslatorService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AutoTranslateService(
    val userService: UserService,
    val fileService: FileService,
    val autoTranslateRepository: AutoTranslateRepository,
    val translatorService: TranslatorService,
    val translateFileService: TranslateFileService,
    val translateResultRepository: TranslateResultRepository,
    val manualTranslateRepository: ManualTranslateRepository,
    val manualResultRepository: ManualResultRepository
) {

    @Transactional
    suspend fun findAutoTranslateEntityById(id: Long): AutoTranslate {
        return autoTranslateRepository.findById(id)
            .orElseThrow { TranslateException("존재 하지 않는 번역 데이터 리스트 입니다.") }
    }

    @Transactional
    suspend fun findAutoTranslateById(id: Long): AutoTranslateDTO {
        return AutoTranslateDTO(
            findAutoTranslateEntityById(id)
        )
    }

    @Transactional
    suspend fun saveTranslateResult(userId: Long, translateResultDTO: TranslateResultDTO): Long? {
        val user = userService.getUserEntity(userId)
        val translateFiles = translateResultDTO.autoTranslate.translateFile.map {
            val file = fileService.findFileEntityById(it.file.id!!)
            it.toEntity(file)
        }.toMutableList()
        val autoTranslate = translateResultDTO.autoTranslate.toEntity(user, translateFiles)
        return translateResultRepository.save(translateResultDTO.toEntity(user, autoTranslate, null)).id
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
        return findAllTranslateResultEntityByUserId(id).map {
            TranslateResultDTO(it)
        }.toMutableList()
    }

    @Transactional
    suspend fun findAllTranslateResultEntityByUserId(id: Long): List<TranslateResult> {
        return translateResultRepository.findAllByUserId(id)
    }


    @Transactional
    suspend fun saveManualResult(resultId: Long, translatorId: Long, resultDTO: ManualResultDTO): Long? {
        val translateResultDTO = findTranslateResult(resultId)

        val user = userService.getUserEntity(translateResultDTO.user.id!!)
        val translator = translatorService.findTranslatorEntityById(translatorId)

        val autoTranslate = findAutoTranslateEntityById(translateResultDTO.autoTranslate.id!!)
        val manualResult = resultDTO.toEntity(translator, mutableListOf()) //처음 저장시에는 내부 데이터는 비어있음.

        val translateResultEntity = translateResultDTO.toEntity(user, autoTranslate, manualResult)
        return translateResultRepository.save(translateResultEntity).id
    }

    @Transactional
    suspend fun update(resultId: Long, requestDTO: ManualTranslateRequestDTO) {
        val translateResult = findTranslateResultEntity(resultId)
        if (translateResult.manualResult == null)
            throw ManualException("생성되지 않은 번역 요청입니다. 결과 ID : $resultId")

        val manualTranslates = requestDTO.data.map {
            val translateFileId = it.file
            val content = it.content
            if (existManualTranslateByTranslateFileId(translateFileId))
                throw ManualException("이미 번역된 내용입니다. 결과 ID : $resultId,  자동번역 파일 ID : $translateFileId")

            val translateFiles = translateResult.autoTranslate.translateFiles
            if (translateFiles.none { it.id == translateFileId })
                throw ManualException("해당 번역 요청에 포함되지 않은 자동 번역 데이터입니다. 결과 ID : $resultId, 자동번역 파일 ID : $translateFileId")

            ManualTranslate(null, translateFileService.findTranslateFileEntity(translateFileId), content)
        }

        translateResult.manualResult!!.manualTranslate.addAll(manualTranslates)
        translateResult.manualResult!!.status = TranslateState.DONE
        // 없애도 될지 체크 -> 안됨
        translateResultRepository.save(translateResult)

    }

    @Transactional(readOnly = true)
    suspend fun manualResultExists(translateResultId: Long): Boolean {
        return findTranslateResult(translateResultId).manualResultDTO != null
    }


    @Transactional(readOnly = true)
    suspend fun existManualTranslateByTranslateFileId(translateFileId: Long): Boolean =
        manualTranslateRepository.existsByTranslateFileId(translateFileId)


    suspend fun deleteManualResultById(id : Long) {
        manualResultRepository.deleteById(id)
    }

    @Transactional
    suspend fun removeTranslatorHistory(translatorId: Long) {
        translateResultRepository.findAll().filter {
            //번역가 번역 요청이 이루어진 경우
            it.manualResult != null && it.manualResult!!.translator?.id == translatorId
        }.forEach { result ->
            val manualResult = result.manualResult!!
            //만약 요청이 완료된경우가 아니라면 요청을 제거
            if (manualResult.status != TranslateState.DONE) {
                manualResult.translator = null
                result.manualResult = null
                deleteManualResultById(manualResult.id!!)
            }
            else
            //요청이 완료됐으면 번역가 데이터를 제거.
                result.manualResult!!.translator = null
        }
        //map을 하게 되면 기존 객체를 쓰는게 아님. 따라서 기존 영속 obj가 가진 데이터가 저장됨.
    }

    @Transactional
    suspend fun deleteUserResult(userId : Long) {
        /** 참조 순서 (File <- TranslateFile <- AutoTranslate <- TranslateResult
        *                             ⬆                          ⬋
        *                         ManualTranslate <- ManualResult
         */
        //ManualResult 제거
        val results = findAllTranslateResultEntityByUserId(userId)
        results.forEach {
            if (it.manualResult != null) {
                val id = it.manualResult!!.id
                it.manualResult = null
                deleteManualResultById(id!!)
            }
        }
        //TranslateResult 제거
        translateResultRepository.deleteAll(results)
    }

}