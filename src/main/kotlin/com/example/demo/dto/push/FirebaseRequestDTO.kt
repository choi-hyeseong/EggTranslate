package com.example.demo.dto.push

data class FirebaseRequestDTO(val id: String, val title: String, val content: String)
//추후 id -> userid값으로, 현재는 token값