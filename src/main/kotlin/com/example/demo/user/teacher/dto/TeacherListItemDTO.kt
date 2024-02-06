package com.example.demo.user.teacher.dto

import com.example.demo.user.teacher.entity.Teacher
import org.springframework.util.Assert

class TeacherListItemDTO(
    val school : String,
    val grade : Int,
    val classname : String
) {
    var id: Long = -1
    var userId: Long = -1

    constructor(teacher: Teacher) : this(teacher.school, teacher.grade, teacher.className) {
        Assert.notNull(teacher.id, "선생님의 id는 null값이 와선 안됩니다.")
        Assert.notNull(teacher.user.id, "선생님의 유저 id는 null값이 와선 안됩니다.")
        this.id = teacher.id!!
        this.userId = teacher.user.id!!
    }
}