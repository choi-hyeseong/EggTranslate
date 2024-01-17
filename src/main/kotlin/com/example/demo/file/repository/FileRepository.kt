package com.example.demo.file.repository

import com.example.demo.file.entity.File
import org.springframework.data.jpa.repository.JpaRepository

interface FileRepository : JpaRepository<File, Long> {

    fun findAllByMemberId(id : Long) : List<File>

    fun existsFileById(id : Long) : Boolean

    fun deleteAllByMemberId(id : Long)

}