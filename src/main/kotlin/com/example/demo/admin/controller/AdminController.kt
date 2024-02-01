package com.example.demo.admin.controller

import com.example.demo.board.dto.BoardEditRequestDTO
import com.example.demo.board.dto.BoardRequestDTO
import com.example.demo.board.dto.BoardResponseDTO
import com.example.demo.board.service.BoardService
import com.example.demo.common.page.Pageable
import com.example.demo.common.response.Response
import com.example.demo.file.service.FileService
import com.example.demo.profile.service.ProfileService
import com.example.demo.user.basic.dto.UserInfoDTO
import com.example.demo.user.basic.dto.UserListItemDTO
import com.example.demo.user.basic.dto.UserResponseDTO
import com.example.demo.user.basic.service.UserService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/admin")
class AdminController(
    private val userService: UserService,
    private val fileService: FileService,
    private val boardService: BoardService,
    private val profileService: ProfileService
) {

    /*
    * Board Part
    */
    @PostMapping("/board/{id}")
    suspend fun write(@PathVariable id : Long, @ModelAttribute boardRequestDTO: BoardRequestDTO) : Response<BoardResponseDTO> {
        val userDto = userService.getUser(id)
        val fileDto = fileService.saveFile(userDto, boardRequestDTO.file)
        return Response.ofSuccess(null, boardService.writeBoard(id, fileDto, boardRequestDTO))
    }

    @PutMapping("/board/{id}")
    suspend fun edit(@PathVariable id : Long, @RequestBody boardEditDTO: BoardEditRequestDTO) : Response<BoardResponseDTO> {
        return Response.ofSuccess("게시글이 수정되었습니다. id : $id", boardService.editBoard(boardEditDTO.toEditDTO(id)))
    }


    @DeleteMapping("/board/{id}")
    suspend fun delete(@PathVariable id : Long) : Response<BoardResponseDTO> {
        return Response.ofSuccess("게시글이 삭제되었습니다. id : $id", boardService.deleteBoard(id))
    }

    /*
    *   User Part
    */
    @GetMapping("/user")
    suspend fun users(@RequestParam(defaultValue = "0") page : Int, @RequestParam(defaultValue = "20") amount : Int) : Response<Pageable<UserListItemDTO>> {
        return Response.ofSuccess(null, userService.getUserList(page, amount))
    }

    @GetMapping("/user/{id}")
    suspend fun userInfo(@PathVariable id : Long) : Response<UserResponseDTO> {
        return Response.ofSuccess(null, userService.getUser(id).toResponseDTO())
    }

    @DeleteMapping("/user/{id}")
    suspend fun deleteUser(@PathVariable id : Long) : Response<UserResponseDTO> {
        profileService.deleteProfile(id)
        return Response.ofSuccess("해당 유저가 삭제되었습니다. id : $id", null)
    }

}