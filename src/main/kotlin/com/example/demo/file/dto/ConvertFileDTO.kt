package com.example.demo.file.dto

import com.example.demo.file.dto.AbstractFileDTO
import com.example.demo.file.entity.ConvertFile


class ConvertFileDTO(
    val id: Long?,
    var saveName: String,
    savePath: String,
) : AbstractFileDTO(savePath) {
    constructor(convertFile: ConvertFile) : this(convertFile.id, convertFile.saveName, convertFile.savePath)

    fun toEntity() = ConvertFile(id, saveName, savePath)
}