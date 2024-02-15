package com.example.demo.member.parent.child.dto

import com.example.demo.member.parent.child.entity.Child

class ChildListItemDTO (
    val id : Long?,
    val name : String
) {
    constructor(child: Child) : this(child.id, child.name)
}