package com.example.demo.voca.component

import com.example.demo.voca.dto.VocaDTO

interface VocaLoader {

    suspend fun load() : List<VocaDTO>
}