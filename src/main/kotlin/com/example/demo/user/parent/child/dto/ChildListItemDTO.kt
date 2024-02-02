package com.example.demo.user.parent.child.dto

import com.example.demo.user.parent.child.entity.Child

class ChildListItemDTO (
    val id : Long?,
    val name : String
) {
    constructor(child: Child) : this(child.id, child.name)
}