package com.example.demo.member.parent.dto

import com.example.demo.member.parent.child.dto.ChildDTO
import io.swagger.v3.oas.annotations.media.Schema

class ParentResponseDTO (
    @Schema(name = "id", description = "해당 유저의 부모 id입니다.")
    val id : Long?,
    @Schema(name = "userId", description = "해당 유저의 id입니다.")
    val userId : Long?,
    @Schema(name = "children", description = "해당 유저의 자식정보입니다.")
    var children : MutableList<ChildDTO>,
)