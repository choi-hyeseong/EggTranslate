package com.example.demo.member.parent.dto

import com.example.demo.member.parent.child.dto.ChildListItemDTO
import com.example.demo.member.parent.entity.Parent
import io.swagger.v3.oas.annotations.media.Schema

class ParentListItemDTO (
    @Schema(name = "id", description = "해당 유저의 부모 id입니다.")
    val id : Long?,
    @Schema(name = "userId", description = "해당 유저의 id입니다.")
    val userId : Long?,
    @Schema(name = "name", description = "해당 유저의 이름입니다.")
    val name : String,
    @Schema(name = "children", description = "해당 유저의 자식 정보입니다.")
    var children : List<ChildListItemDTO>,
) {
    constructor(parent : Parent) : this(parent.id, parent.user.id, parent.user.name, parent.children.map { ChildListItemDTO(it) })
}