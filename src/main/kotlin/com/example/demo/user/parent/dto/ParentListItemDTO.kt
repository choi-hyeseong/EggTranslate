package com.example.demo.user.parent.dto

import com.example.demo.user.parent.child.dto.ChildListItemDTO
import com.example.demo.user.parent.entity.Parent

class ParentListItemDTO (
    val id : Long?,
    val userId : Long?,
    val name : String,
    var children : List<ChildListItemDTO>,
) {
    constructor(parent : Parent) : this(parent.id, parent.user.id, parent.user.name, parent.children.map { ChildListItemDTO(it) })
}