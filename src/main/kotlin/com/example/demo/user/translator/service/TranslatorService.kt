package com.example.demo.user.translator.service

import com.example.demo.common.page.Pageable
import com.example.demo.profile.dto.TranslatorEditDTO
import com.example.demo.user.basic.data.DataFetcher
import com.example.demo.user.basic.exception.UserNotFoundException
import com.example.demo.user.basic.service.UserService
import com.example.demo.user.teacher.dto.TeacherListItemDTO
import com.example.demo.user.translator.dto.TranslatorDTO
import com.example.demo.user.translator.dto.TranslatorListItemDTO
import com.example.demo.user.translator.dto.TranslatorResponseDTO
import com.example.demo.user.translator.entity.Translator
import com.example.demo.user.translator.repository.TranslatorRepository
import org.springframework.data.domain.PageRequest
import org.springframework.transaction.annotation.Transactional
import org.springframework.stereotype.Service
import kotlin.math.max

@Service
class TranslatorService(
    private val userService: UserService,
    private val translatorRepository: TranslatorRepository
) : DataFetcher<TranslatorListItemDTO, TranslatorResponseDTO> {

    @Transactional
    suspend fun signUp(translatorDTO: TranslatorDTO): Long? {
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



    /*
    * JPA Section
    */


    @Transactional
    suspend fun findTranslatorEntityById(id: Long): Translator =
        translatorRepository.findById(id).orElseThrow { UserNotFoundException(id, "찾을 수 없는 번역가 id입니다.") }

    @Transactional(readOnly = true)
    suspend fun findTranslatorByUserId(id: Long): TranslatorDTO =
        TranslatorDTO(translatorRepository
            .findByUserId(id)
            .orElseThrow { UserNotFoundException(id, "해당 id로 번역가를 찾을 수 없습니다.") }
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