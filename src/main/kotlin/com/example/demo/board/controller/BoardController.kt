package com.example.demo.board.controller

import com.example.demo.board.dto.BoardListItemDTO
import com.example.demo.board.dto.BoardRequestDTO
import com.example.demo.board.dto.BoardResponseDTO
import com.example.demo.common.page.Pageable
import com.example.demo.board.service.BoardService
import com.example.demo.common.response.Response
import com.example.demo.file.service.FileService
import com.example.demo.user.basic.dto.UserDto
import com.example.demo.user.basic.service.UserService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.enums.ParameterIn
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/board")
@Tag(name = "board", description = "공지사항 조회 api입니다.")
class BoardController(
    private val boardService: BoardService,
) {

    @Operation(
        summary = "공지사항 상세 조회", responses = [
            ApiResponse(
                responseCode = "200",
                description = "조회 성공",
                content = [Content(mediaType = "application/json")],
                useReturnTypeSchema = true //for generic return value
            ),
            ApiResponse(
                responseCode = "403",
                description = "조회 실패",
                content = []
            )
        ]
    )
    @GetMapping("/{id}")
    suspend fun getBoard(
        @Parameter(name = "id", `in` = ParameterIn.PATH, required = true, description = "해당 id를 가진 공지사항의 상세정보를 조회합니다.")
        @PathVariable(required = true, value = "id") id: Long
    ): BoardResponseDTO {
        val response = boardService.getDetail(id)
        boardService.increaseViewCount(response.id)
        //todo board가 invisible이면 user가 admin이 아니면 못봄
        return response
    }

    @GetMapping
    @Operation(
        summary = "공지사항 목록 조회", responses = [
            ApiResponse(
                responseCode = "200",
                description = "조회 성공",
                content = [Content(mediaType = "application/json")],
                useReturnTypeSchema = true //for generic return value
            ),
            ApiResponse(
                responseCode = "403",
                description = "조회 실패",
                content = []
            )
        ]
    )
    suspend fun getBoardList(
        @Parameter(name = "page", description = "조회할 페이지 입니다.", required = false, `in` = ParameterIn.QUERY)
        @RequestParam(defaultValue = "0")
        page: Int,
        @Parameter(name = "amount", description = "한 페이지당 표시할 갯수입니다.", required = false, `in` = ParameterIn.QUERY)
        @RequestParam(defaultValue = "20")
        amount: Int
    ): Response<Pageable<BoardListItemDTO>> {
        return Response.ofSuccess(null, boardService.getList(page, amount))
    }
}