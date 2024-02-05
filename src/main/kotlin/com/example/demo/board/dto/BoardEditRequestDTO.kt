package com.example.demo.board.dto

import com.example.demo.board.type.BoardVisibility
import io.swagger.v3.oas.annotations.media.Schema

class BoardEditRequestDTO (
    @Schema(name = "title", description = "수정할 글의 제목입니다. 미지정시 기존 제목이 유지됩니다.", required = false)
    val title : String?,
    @Schema(name = "content", description = "수정할 글의 내용입니다. 미지정시 기존 내용이 유지됩니다.", required = false)
    val content : String?,
    @Schema(name = "visibility", description = "수정할 글의 공개 여부입니다. 미지정시 기존 공개여부가 유지됩니다.", required = false)
    val visibility: BoardVisibility?,
    @Schema(name = "files", description =
    "수정할 글에 할당될 파일 id값입니다. 미지정시 기존 파일이 유지됩니다.\n\n만약 1,2라는 id의 파일을 가진 게시글에서 id 1번 파일을 제거하고 싶으면 요청시 files에 [2]로 요청하면 됩니다. \n\n만약 모든 파일을 제거하고 싶으면 files를 null이 아닌 [] empty 상태로 보내면 됩니다.", required = false)
    val files : List<Long>?
) {
    fun toEditDTO(id : Long) = BoardEditDTO(id, title, content, visibility, files)
}