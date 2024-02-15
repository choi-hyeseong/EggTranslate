package com.example.demo.admin.controller

import com.example.demo.admin.dto.AdminSignUpDTO
import com.example.demo.admin.service.AdminBoardService
import com.example.demo.admin.service.AdminUserService
import com.example.demo.auth.security.config.getUserOrThrow
import com.example.demo.board.dto.BoardEditRequestDTO
import com.example.demo.board.dto.BoardRequestDTO
import com.example.demo.board.dto.BoardResponseDTO
import com.example.demo.common.page.Pageable
import com.example.demo.common.response.Response
import com.example.demo.file.service.FileService
import com.example.demo.signup.dto.TranslatorSignUpDTO
import com.example.demo.signup.validation.SignUpValid
import com.example.demo.member.user.dto.UserDto
import com.example.demo.member.user.dto.UserListItemDTO
import com.example.demo.member.user.dto.UserResponseDTO
import com.example.demo.member.user.service.UserService
import com.example.demo.member.parent.dto.*
import com.example.demo.member.teacher.dto.*
import com.example.demo.member.translator.dto.*
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.enums.ParameterIn
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/admin")
@Tag(name = "admin", description = "관리자 전용 api입니다.")
class AdminController(
    private val userService: UserService,
    private val fileService: FileService,
    private val adminBoardService: AdminBoardService,
    private val adminUserService: AdminUserService
) {

    /*
    * Board Part
    */
    @PostMapping("/board")
    @Operation(
        summary = "게시글 작성하기", description = "게시글을 작성합니다.", responses = [
            ApiResponse(
                responseCode = "200",
                description = "성공",
                content = [Content(mediaType = "application/json")],
                useReturnTypeSchema = true
            ),
            ApiResponse(
                responseCode = "403",
                description = "실패",
                content = []
            )]
    )
    suspend fun write(
        authentication: Authentication?,
        @ModelAttribute boardRequestDTO: BoardRequestDTO
    ): Response<BoardResponseDTO> {
        val user = authentication.getUserOrThrow()
        val userDto = userService.findUserByUserName(user.username)
        val fileDto = fileService.saveFile(userDto, boardRequestDTO.file)
        return Response.ofSuccess(null, adminBoardService.write(userDto.id!!, fileDto, boardRequestDTO))
    }

    @Operation(
        summary = "게시글 수정하기", description = "게시글을 수정합니다.", responses = [
            ApiResponse(
                responseCode = "200",
                description = "성공",
                content = [Content(mediaType = "application/json")],
                useReturnTypeSchema = true
            ),
            ApiResponse(
                responseCode = "403",
                description = "실패",
                content = []
            )]
    )
    @PutMapping("/board/{id}")
    suspend fun edit(
        @Parameter(name = "id", `in` = ParameterIn.PATH, description = "수정할 게시글의 id입니다.")
        @PathVariable id: Long, @RequestBody boardEditDTO: BoardEditRequestDTO
    ): Response<BoardResponseDTO> {
        return Response.ofSuccess("게시글이 수정되었습니다. id : $id", adminBoardService.edit(id, boardEditDTO))
    }


    @Operation(
        summary = "게시글 삭제하기", description = "게시글을 삭제합니다.", responses = [
            ApiResponse(
                responseCode = "200",
                description = "성공",
                content = [Content(mediaType = "application/json")],
                useReturnTypeSchema = true
            ),
            ApiResponse(
                responseCode = "403",
                description = "실패",
                content = []
            )]
    )
    @DeleteMapping("/board/{id}")
    suspend fun delete(
        @Parameter(name = "id", description = "해당 게시글의 id입니다.", `in` = ParameterIn.PATH, required = true)
        @PathVariable id: Long
    ): Response<BoardResponseDTO> {
        return Response.ofSuccess("게시글이 삭제되었습니다. id : $id", adminBoardService.delete(id))
    }

    /*
    *   User Part
    */
    @GetMapping("/user")
    @Operation(
        summary = "유저 목록 확인하기", description = "유저 목록을 확인합니다.", responses = [
            ApiResponse(
                responseCode = "200",
                description = "성공",
                content = [Content(mediaType = "application/json")],
                useReturnTypeSchema = true
            ),
            ApiResponse(
                responseCode = "403",
                description = "실패",
                content = []
            )]
    )
    suspend fun users(
        @Parameter(name = "page", description = "조회할 페이지 입니다.", required = false, `in` = ParameterIn.QUERY)
        @RequestParam(defaultValue = "0")
        page: Int,
        @Parameter(name = "amount", description = "한 페이지당 표시할 갯수입니다.", required = false, `in` = ParameterIn.QUERY)
        @RequestParam(defaultValue = "20")
        amount: Int
    ): Response<Pageable<UserListItemDTO>> {
        return Response.ofSuccess(null, adminUserService.findUserList(page, amount))
    }

    @Operation(
        summary = "유저 상세 정보 조회하기", description = "유저의 상세 정보를 조회합니다.", responses = [
            ApiResponse(
                responseCode = "200",
                description = "성공",
                content = [Content(mediaType = "application/json")],
                useReturnTypeSchema = true
            ),
            ApiResponse(
                responseCode = "403",
                description = "실패",
                content = []
            )]
    )
    @GetMapping("/user/{id}")
    suspend fun userInfo(
        @Parameter(name = "id", description = "유저의 id입니다.", `in` = ParameterIn.PATH)
        @PathVariable id: Long): Response<UserResponseDTO> {
        return Response.ofSuccess(null, adminUserService.findUserDetail(id))
    }

    //force를 true로 주면 parent, translator, teacher 데이터 삭제를 무시하고 user를 삭제함 (user 데이터만 남아있는경우 사용할것)
    @Operation(
        summary = "유저 삭제하기", description = "유저를 삭제합니다", responses = [
            ApiResponse(
                responseCode = "200",
                description = "성공",
                content = [Content(mediaType = "application/json")],
                useReturnTypeSchema = true
            ),
            ApiResponse(
                responseCode = "403",
                description = "실패",
                content = []
            )]
    )
    @DeleteMapping("/user/{id}")
    suspend fun deleteUser(
        @Parameter(name = "id", description = "유저의 id입니다.", `in` = ParameterIn.PATH)
        @PathVariable id: Long,
        @Parameter(name = "force", description = "강제삭제 여부입니다. 미지정 가능하며, 유저가 삭제되지 않을때 사용해주세요.", required = false)
        @RequestParam(defaultValue = "false") force: Boolean
    ): Response<UserResponseDTO> {
        adminUserService.deleteUser(id, force)
        return Response.ofSuccess("해당 유저가 삭제되었습니다. id : $id", null)
    }

    /*
    *   Parent Part, id는 userId로 고정.
    */
    @GetMapping("/user/parent")
    suspend fun parents(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "20") amount: Int
    ): Response<Pageable<ParentListItemDTO>> {
        return Response.ofSuccess(null, adminUserService.findParentList(page, amount))
    }

    @GetMapping("/user/parent/{id}")
    suspend fun parentInfo(@PathVariable id: Long): Response<ParentResponseDTO> {
        return Response.ofSuccess(null, adminUserService.findParentDetail(id))
    }

    //해당 유저를 parent로 설정함. (번역가든, 교사이든 데이터 제거하고)
    @PostMapping("/user/parent/{id}")
    suspend fun convertParent(
        @PathVariable id: Long,
        @RequestBody parentConvertDTO: ParentConvertDTO
    ): Response<Nothing> {
        val response = adminUserService.convertToParent(id, parentConvertDTO)
        return Response.ofSuccess("해당 유저를 부모 회원으로 변경하였습니다. User Id : $id Parent Id : $response", null)
    }

    //유저 정보도 업데이트 하기 때문에 좀더 상세한 정보를 담는 ParentDTO를 반환.
    @PutMapping("/user/parent/{id}")
    suspend fun updateParent(
        @PathVariable id: Long,
        @RequestBody parentUpdateDTO: ParentUpdateDTO
    ): Response<ParentDTO> {
        return Response.ofSuccess(null, adminUserService.updateParent(id, parentUpdateDTO))
    }

    /*
    * Teacher Part
    */
    @GetMapping("/user/teacher")
    suspend fun teachers(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "20") amount: Int
    ): Response<Pageable<TeacherListItemDTO>> {
        return Response.ofSuccess(null, adminUserService.findTeacherList(page, amount))
    }

    @GetMapping("/user/teacher/{id}")
    suspend fun teacherInfo(@PathVariable id: Long): Response<TeacherResponseDTO> {
        return Response.ofSuccess(null, adminUserService.findTeacherDetail(id))
    }

    @PostMapping("/user/teacher/{id}")
    suspend fun convertTeacher(
        @PathVariable id: Long,
        @RequestBody teacherConvertDTO: TeacherConvertDTO
    ): Response<Nothing> {
        val response = adminUserService.convertToTeacher(id, teacherConvertDTO)
        return Response.ofSuccess("해당 유저를 선생 회원으로 변경하였습니다. User Id : $id Teacher Id : $response", null)
    }

    @PutMapping("/user/teacher/{id}")
    suspend fun updateTeacher(
        @PathVariable id: Long,
        @RequestBody teacherUpdateDTO: TeacherUpdateDTO
    ): Response<TeacherDTO> {
        return Response.ofSuccess(null, adminUserService.updateTeacher(id, teacherUpdateDTO))
    }

    /*
    *   Translator Part
    */
    @GetMapping("/user/translator")
    suspend fun translators(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "20") amount: Int
    ): Response<Pageable<TranslatorListItemDTO>> {
        return Response.ofSuccess(null, adminUserService.findTranslatorList(page, amount))
    }

    //번역가 회원가입 시키기.
    @PostMapping("/user/translator")
    suspend fun registerTranslator(@SignUpValid @RequestBody translatorSignUpDTO: TranslatorSignUpDTO): Response<TranslatorDTO> {
        val response = adminUserService.registerTranslator(translatorSignUpDTO)
        return Response.ofSuccess(
            "번역가 유저 회원가입에 성공하였습니다. User Id : ${response.user.id} Translator Id : ${response.id}",
            response
        )
    }

    @GetMapping("/user/translator/{id}")
    suspend fun translatorInfo(@PathVariable id: Long): Response<TranslatorResponseDTO> {
        return Response.ofSuccess(null, adminUserService.findTranslatorDetail(id))
    }

    @PostMapping("/user/translator/{id}")
    suspend fun convertTranslator(
        @PathVariable id: Long,
        @RequestBody translatorConvertDTO: TranslatorConvertDTO
    ): Response<Nothing> {
        val response = adminUserService.convertToTranslator(id, translatorConvertDTO)
        return Response.ofSuccess("해당 유저를 번역가 회원으로 변경하였습니다. User Id : $id Transaltor Id : $response", null)
    }

    @PutMapping("/user/translator/{id}")
    suspend fun updateTranslator(
        @PathVariable id: Long,
        @RequestBody translatorUpdateDTO: TranslatorUpdateDTO
    ): Response<TranslatorDTO> {
        return Response.ofSuccess(null, adminUserService.updateTranslator(id, translatorUpdateDTO))
    }

    /*
    *  Admin Part
    */
    @PutMapping("/{id}")
    suspend fun convertAdmin(@PathVariable id: Long): Response<UserDto> {
        return Response.ofSuccess("해당 유저가 관리자로 설정되었습니다.", adminUserService.convertAdmin(id))
    }


    @PostMapping("")
    suspend fun createAdmin(@SignUpValid @RequestBody adminSignUpDTO: AdminSignUpDTO): Response<UserDto> {
        return Response.ofSuccess("관리자가 추가되었습니다.", adminUserService.createAdmin(adminSignUpDTO))
    }
}