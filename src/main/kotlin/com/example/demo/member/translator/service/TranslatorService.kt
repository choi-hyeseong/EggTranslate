package com.example.demo.member.translator.service

import com.example.demo.common.page.Pageable
import com.example.demo.member.user.data.DataFetcher
import com.example.demo.member.user.data.DataUpdater
import com.example.demo.member.exception.UserException
import com.example.demo.member.user.service.UserService
import com.example.demo.member.translator.dto.*
import com.example.demo.member.translator.entity.Translator
import com.example.demo.member.translator.repository.TranslatorRepository
import org.springframework.data.domain.PageRequest
import org.springframework.transaction.annotation.Transactional
import org.springframework.stereotype.Service
import kotlin.math.max

@Service
class TranslatorService(
    private val userService: UserService,
    private val translatorRepository: TranslatorRepository
) : DataFetcher<TranslatorListItemDTO, TranslatorResponseDTO>, DataUpdater<TranslatorConvertDTO, TranslatorUpdateDTO, TranslatorDTO> {

    @Transactional
    suspend fun createTranslator(translatorDTO: TranslatorDTO): Long? {
        val user = userService.getUserEntity(translatorDTO.user.id!!)
        return translatorRepository.save(translatorDTO.toEntity(user)).id
    }

    @Transactional
    override suspend fun getList(page: Int, amount: Int): Pageable<TranslatorListItemDTO> {
        val pageResult = translatorRepository.findAll(PageRequest.of(page, amount))
        return Pageable(page, max(0, pageResult.totalPages - 1), pageResult.content.map { TranslatorListItemDTO(it) })
    }

    @Transactional
    override suspend fun getDetail(id: Long): TranslatorResponseDTO {
        return findTranslatorByUserId(id).toResponseDTO()
    }

    @Transactional
    override suspend fun convert(id: Long, convertDTO: TranslatorConvertDTO): Long? {
        val userDTO = userService.getUser(id)
        return createTranslator(convertDTO.toTranslatorDTO(userDTO))
    }

    @Transactional
    override suspend fun update(id: Long, updateDTO: TranslatorUpdateDTO): TranslatorDTO {
        val translator = findTranslatorEntityByUserId(id)
        print(updateDTO.user)
        userService.updateUser(id, updateDTO.user) //유저 업데이트
        translator.update(updateDTO)
        return TranslatorDTO(translatorRepository.save(translator))
    }

    /*
    * JPA Section
    */
    @Transactional
    suspend fun findTranslatorEntityByUserId(userId: Long): Translator =
        translatorRepository.findByUserId(userId).orElseThrow { UserException("찾을 수 없는 번역가 id입니다. id : $userId") }


    @Transactional
    suspend fun findTranslatorEntityById(id: Long): Translator =
        translatorRepository.findById(id).orElseThrow { UserException("찾을 수 없는 번역가 id입니다. id : $id") }

    @Transactional(readOnly = true)
    suspend fun findTranslatorByUserId(id: Long): TranslatorDTO =
        TranslatorDTO(translatorRepository
            .findByUserId(id)
            .orElseThrow { UserException("해당 id로 번역가를 찾을 수 없습니다. id : $id") }
        )

    @Transactional(readOnly = true)
    suspend fun findTranslatorById(id: Long): TranslatorDTO =
        TranslatorDTO(
            findTranslatorEntityById(id)
        )

    @Transactional
    suspend fun delete(id: Long) {
        translatorRepository.deleteById(id)
    }



}