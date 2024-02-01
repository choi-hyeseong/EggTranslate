package com.example.demo.profile.service

import com.example.demo.board.service.BoardService
import com.example.demo.docs.service.DocumentService
import com.example.demo.file.service.FileService
import com.example.demo.profile.dto.ParentEditDTO
import com.example.demo.profile.dto.TeacherEditDTO
import com.example.demo.profile.dto.TranslatorEditDTO
import com.example.demo.profile.dto.UserEditDTO
import com.example.demo.translate.auto.service.TranslateManageService
import com.example.demo.translate.auto.service.TranslateResultService
import com.example.demo.user.basic.service.UserService
import com.example.demo.user.basic.type.UserType
import com.example.demo.user.heart.service.HeartService
import com.example.demo.user.parent.service.ParentService
import com.example.demo.user.teacher.service.TeacherService
import com.example.demo.user.translator.service.TranslatorService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class ProfileService(
    private val userService: UserService,
    private val parentService: ParentService,
    private val teacherService: TeacherService,
    private val documentService: DocumentService,
    private val translatorService: TranslatorService,
    private val translateManageService: TranslateManageService,
    private val fileService: FileService,
    private val heartService: HeartService,
    private val boardService: BoardService
    ) {

    suspend fun updateUser(id : Long, userEditDto: UserEditDTO) {
        userService.updateProfile(id, userEditDto)
    }

    suspend fun updateParent(id : Long, parentEditDTO: ParentEditDTO) {
        //user 정보 업데이트
        val dto = parentEditDTO.user
        updateUser(id, dto)
        //부모 단독 정보 업데이트
        parentService.updateProfile(id, parentEditDTO)
    }

    suspend fun updateTeacher(id : Long, teacherEditDTO: TeacherEditDTO) {
        //user 정보 업데이트
        val dto = teacherEditDTO.user
        updateUser(id, dto)
        //선생 단독 정보 업데이트
        teacherService.updateProfile(id, teacherEditDTO)
    }

    suspend fun updateTranslator (id : Long, translatorEditDTO: TranslatorEditDTO) {
        //user 정보 업데이트
        val dto = translatorEditDTO.user
        updateUser(id, dto)
        //번역가 단독 정보 업데이트
        translatorService.updateProfile(id, translatorEditDTO)
    }

    @Transactional
    suspend fun deleteProfile(id : Long) {
        val user = userService.getUser(id)
        when(user.userType) {
            //support (UserType)해서 깔끔하게 하는것도 좋을듯
            UserType.TEACHER -> teacherService.deleteByUserId(id)
            UserType.TRANSLATOR -> deleteTranslator(id)
            UserType.PARENT -> deleteParent(id)
            else -> {} //구현 없음.
        }
        deleteUser(user.id!!)
    }

    @Transactional
    suspend fun deleteTranslator(id : Long) {
        val translator = translatorService.findTranslatorByUserId(id)
        translateManageService.removeTranslatorHistory(translator.id!!)
        heartService.removeTranslatorHeart(id)
        translatorService.delete(translator.id)
    }

    @Transactional
    suspend fun deleteUser(id : Long) {
        translateManageService.deleteUserResult(id)
        heartService.removeUserHeart(id)
        fileService.deleteFileByUserId(id)
        boardService.deleteAllBoardByUserId(id) //보드와 연관된 데이터 다 지우고 해야 외래키 끊어짐
        documentService.deleteAllDocumentByUserId(id)
        userService.deleteById(id)
    }

    @Transactional
    suspend fun deleteParent(userId : Long) {
        translateManageService.removeAllChild(parentService.findByParentUserId(userId).children.map { it.id!! })
        parentService.deleteByUserId(userId)
    }
}