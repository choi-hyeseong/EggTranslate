package com.example.demo.translate.auto.service

import com.example.demo.translate.auto.entity.TranslateResult
import com.example.demo.translate.manual.dto.ManualResultDTO
import com.example.demo.translate.manual.dto.ManualTranslateRequestDTO
import com.example.demo.translate.manual.entity.ManualTranslate
import com.example.demo.translate.manual.exception.ManualException
import com.example.demo.translate.manual.type.TranslateState
import com.example.demo.user.basic.service.UserService
import com.example.demo.user.translator.service.TranslatorService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
//순환 참조를 해결하기 위한 Facade Service
class TranslateManageService(
    private val autoTranslateService: AutoTranslateService,
    private val translateResultService: TranslateResultService,
    private val manualResultService: ManualResultService,
    private val translateFileService: TranslateFileService,
    private val userService: UserService,
    private val translatorService: TranslatorService
){

    @Transactional
    suspend fun deleteUserResult(userId : Long) {
        /** 참조 순서 (File <- TranslateFile <- AutoTranslate <- TranslateResult
         *                             ⬆                          ⬋
         *                         ManualTranslate <- ManualResult
         */
        //ManualResult 제거
        val results = translateResultService.findAllTranslateResultEntityByUserId(userId)
        results.forEach {
            if (it.manualResult != null) {
                val id = it.manualResult!!.id
                it.manualResult = null
                manualResultService.deleteManualResultById(id!!)
            }
        }
        //TranslateResult 제거
        translateResultService.deleteAll(results)
    }

    @Transactional
    suspend fun update(resultId: Long, requestDTO: ManualTranslateRequestDTO) {
        val translateResult = translateResultService.findTranslateResultEntity(resultId)
        if (translateResult.manualResult == null)
            throw ManualException("생성되지 않은 번역 요청입니다. 결과 ID : $resultId")

        val manualTranslates = requestDTO.data.map {
            val translateFileId = it.file
            val content = it.content
            if (manualResultService.existManualTranslateByTranslateFileId(translateFileId))
                throw ManualException("이미 번역된 내용입니다. 결과 ID : $resultId,  자동번역 파일 ID : $translateFileId")

            val translateFiles = translateResult.autoTranslate.translateFiles
            if (translateFiles.none { it.id == translateFileId })
                throw ManualException("해당 번역 요청에 포함되지 않은 자동 번역 데이터입니다. 결과 ID : $resultId, 자동번역 파일 ID : $translateFileId")

            ManualTranslate(null, translateFileService.findTranslateFileEntity(translateFileId), content)
        }

        translateResult.manualResult!!.manualTranslate.addAll(manualTranslates)
        translateResult.manualResult!!.status = TranslateState.DONE
        // 없애도 될지 체크 -> 안됨
        translateResultService.save(translateResult)

    }

    @Transactional
    suspend fun removeTranslatorHistory(translatorId: Long) {
        translateResultService.findAllEntities().filter {
            //번역가 번역 요청이 이루어진 경우
            it.manualResult != null && it.manualResult!!.translator?.id == translatorId
        }.forEach { result ->
            val manualResult = result.manualResult!!
            //만약 요청이 완료된경우가 아니라면 요청을 제거
            if (manualResult.status != TranslateState.DONE) {
                manualResult.translator = null
                result.manualResult = null
                manualResultService.deleteManualResultById(manualResult.id!!)
            }
            else
            //요청이 완료됐으면 번역가 데이터를 제거.
                result.manualResult!!.translator = null
        }
        //map을 하게 되면 기존 객체를 쓰는게 아님. 따라서 기존 영속 obj가 가진 데이터가 저장됨.
    }

    @Transactional
    suspend fun saveManualResult(resultId: Long, translatorId: Long, resultDTO: ManualResultDTO): Long? {
        val translateResultDTO = translateResultService.findTranslateResult(resultId)

        val user = userService.getUserEntityOrNull(translateResultDTO.user?.id)
        val translator = translatorService.findTranslatorEntityById(translatorId)

        val autoTranslate = autoTranslateService.findAutoTranslateEntityById(translateResultDTO.autoTranslate.id!!)
        val manualResult = resultDTO.toEntity(translator, mutableListOf()) //처음 저장시에는 내부 데이터는 비어있음.

        val translateResultEntity = translateResultDTO.toEntity(user, autoTranslate, manualResult)
        return translateResultService.save(translateResultEntity)
    }

    @Transactional
    suspend fun removeChild(childId: Long) {
        val response = translateResultService.findAllEntities().filter {
            //자식 요청이 들어간경우
            it.child != null && it.child!!.id == childId
        }
        response.forEach {
            it.child = null
        }
        translateResultService.saveAll(response)
    }

    @Transactional
    suspend fun removeAllChild(childId: List<Long>) {
        val response = translateResultService.findAllEntities().filter {
            //자식 요청이 들어간경우
            it.child != null && childId.contains(it.child!!.id!!) //child의 id로 비교해야지..
        }
        response.forEach {
            it.child = null
        }
        translateResultService.saveAll(response)
    }


}