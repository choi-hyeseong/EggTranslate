package com.example.demo.member.parent.child.dto

import com.example.demo.member.parent.child.entity.Child
import io.swagger.v3.oas.annotations.media.Schema

class ChildListItemDTO (
    @Schema(name = "id", description = "자식의 id입니다.")
    val id : Long?,
    @Schema(name = "name", description = "자식의 이름입니다.")
    val name : String
) {
    constructor(child: Child) : this(child.id, child.name)
}