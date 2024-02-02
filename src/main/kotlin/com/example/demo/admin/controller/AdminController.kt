package com.example.demo.admin.controller

import com.example.demo.admin.service.AdminBoardService
import com.example.demo.admin.service.AdminUserService
import com.example.demo.board.dto.BoardEditRequestDTO
import com.example.demo.board.dto.BoardRequestDTO
import com.example.demo.board.dto.BoardResponseDTO
import com.example.demo.board.service.BoardService
import com.example.demo.common.page.Pageable
import com.example.demo.common.response.Response
import com.example.demo.file.service.FileService
import com.example.demo.profile.service.ProfileService
import com.example.demo.user.basic.dto.UserListItemDTO
import com.example.demo.user.basic.dto.UserResponseDTO
import com.example.demo.user.basic.service.UserService
import com.example.demo.user.basic.type.UserType
import com.example.demo.user.parent.dto.ParentConvertDTO
import com.example.demo.user.parent.dto.ParentListItemDTO
import com.example.demo.user.parent.dto.ParentResponseDTO
import com.example.demo.user.parent.service.ParentService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/admin")
class AdminController(
    private val userService: UserService,
    private val fileService: FileService,
    private val adminBoardService: AdminBoardService,
    private val adminUserService: AdminUserService
) {

    /*
    * Board Part
    */
    @PostMapping("/board/{id}")
    suspend fun write(@PathVariable id : Long, @ModelAttribute boardRequestDTO: BoardRequestDTO) : Response<BoardResponseDTO> {
        val userDto = userService.getUser(id)
        val fileDto = fileService.saveFile(userDto, boardRequestDTO.file)
        return Response.ofSuccess(null, adminBoardService.write(id, fileDto, boardRequestDTO))
    }

    @PutMapping("/board/{id}")
    suspend fun edit(@PathVariable id : Long, @RequestBody boardEditDTO: BoardEditRequestDTO) : Response<BoardResponseDTO> {
        return Response.ofSuccess("게시글이 수정되었습니다. id : $id", adminBoardService.edit(id, boardEditDTO))
    }


    @DeleteMapping("/board/{id}")
    suspend fun delete(@PathVariable id : Long) : Response<BoardResponseDTO> {
        return Response.ofSuccess("게시글이 삭제되었습니다. id : $id", adminBoardService.delete(id))
    }

    /*
    *   User Part
    */
    @GetMapping("/user")
    suspend fun users(@RequestParam(defaultValue = "0") page : Int, @RequestParam(defaultValue = "20") amount : Int) : Response<Pageable<UserListItemDTO>> {
        return Response.ofSuccess(null, adminUserService.findUserList(page, amount))
    }

    @GetMapping("/user/{id}")
    suspend fun userInfo(@PathVariable id : Long) : Response<UserResponseDTO> {
        return Response.ofSuccess(null,adminUserService.findUserDetail(id))
    }

    //force를 true로 주면 parent, translator, teacher 데이터 삭제를 무시하고 user를 삭제함 (user 데이터만 남아있는경우 사용할것)
    @DeleteMapping("/user/{id}")
    suspend fun deleteUser(@PathVariable id : Long, @RequestParam(defaultValue = "false") force : Boolean) : Response<UserResponseDTO> {
        adminUserService.deleteUser(id, force)
        return Response.ofSuccess("해당 유저가 삭제되었습니다. id : $id", null)
    }

    /*
    *   Parent Part, id는 userId로 고정.
    */
    @GetMapping("/user/parent")
    suspend fun parents(@RequestParam(defaultValue = "0") page : Int, @RequestParam(defaultValue = "20") amount : Int) : Response<Pageable<ParentListItemDTO>> {
        return Response.ofSuccess(null, adminUserService.findParentList(page, amount))
    }

    @GetMapping("/user/parent/{id}")
    suspend fun parentInfo(@PathVariable id : Long) : Response<ParentResponseDTO> {
        return Response.ofSuccess(null, adminUserService.findParentDetail(id))
    }

    //해당 유저를 parent로 설정함. (번역가든, 교사이든 데이터 제거하고)
    @PostMapping("/user/parent/{id}")
    suspend fun convertParent(@PathVariable id : Long, @RequestBody parentConvertDTO: ParentConvertDTO) : Response<Nothing> {
        val response = adminUserService.convertToParent(id, parentConvertDTO)
        return Response.ofSuccess("해당 유저를 부모 회원으로 변경하였습니다. User Id : $id Parent Id : $response", null)
    }

    @PutMapping("/user/parent/{id}")
    suspend fun updateParent(@PathVariable id : Long) : Response<UserResponseDTO> {
        return Response.ofSuccess(null, userService.getUser(id).toResponseDTO())
    }
}