package com.example.demo.translate.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/translate")
class TranslateController {

    @GetMapping("/{id}")
    fun getRequest(@PathVariable id: Int) {

    }

    @GetMapping("")
    fun getAllRequest() {

    }

    @PostMapping("/{id}")
    fun createRequest(@PathVariable id: Int) {

    }


    @PutMapping("/{id}")
    fun updateRequest(@PathVariable id: Int) {

    }
}