package com.example.demo.file.basic.dto

import com.example.demo.user.basic.dto.UserDto

class FileDTO(
        val origin_name : String,
        val save_name : String,
        val user : UserDto
        val translate_file : List<TranslateFileDTO>
){

}